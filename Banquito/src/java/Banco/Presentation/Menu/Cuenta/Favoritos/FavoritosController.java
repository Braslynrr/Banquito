/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Favoritos;

import Banco.Logic.Account;
import Banco.Logic.Client;
import Banco.Presentation.Menu.Cuenta.Transaccion.TransaccionModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Brazza
 */
@WebServlet(name = "FavoritosController", urlPatterns = {"/presentation/Menu/Cuenta/Favorito/show","/presentation/Menu/Cuenta/Favorito/transfer","/presentation/Menu/Cuenta/Favorito/Eliminar"
,"/presentation/Menu/Cuenta/Favorito/Añade","/presentation/Menu/Cuenta/Favorito/Eok","/presentation/Menu/Cuenta/Favorito/Aok"})
public class FavoritosController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String viewUrl="";
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cuenta/Favorito/show":
               request.setAttribute("model", new TransaccionModel());
               viewUrl=this.show(request);
            break;     
        case "/presentation/Menu/Cuenta/Favorito/transfer":
                viewUrl=this.transfer(request);
            break;
        case "/presentation/Menu/Cuenta/Favorito/Eliminar":
                viewUrl=this.elimina(request);
            break;
        case "/presentation/Menu/Cuenta/Favorito/Añade":
                viewUrl=this.añade(request);
            break;
        case "/presentation/Menu/Cuenta/Favorito/Eok":
            viewUrl=this.eok(request);
            break;
        case "/presentation/Menu/Cuenta/Favorito/Aok":
            viewUrl=this.aok(request);
            break;
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String show(HttpServletRequest request) {
        return this.showAction(request);
    }

    private String showAction(HttpServletRequest request) {
         FavoritosModel model = new FavoritosModel();
         HttpSession session = request.getSession(true);
         String cod_account= (String) request.getParameter("numeroFld");
         List<Account> favs =( List<Account> ) session.getAttribute("favorites");
         for(Account a:favs){
             if((a.getNumber()+"").equals(cod_account)){
                 model.setAccount(a);
                 break;
             }
         }
         try{
            session.setAttribute("model", model);
            return "/presentation/Menu/Cuenta/Favoritos/view.jsp";
         }catch(Exception e){
             
         }
         return "";
    }
    
    
    private boolean comprobar(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        FavoritosModel model= (FavoritosModel) session.getAttribute("model");
        String Nacc=request.getParameter("Naccount");
        model.setDetalle(request.getParameter("detalle"));
        List<Account> accounts= (List<Account>) session.getAttribute("cuentas");
        for(Account a:accounts){
             if((a.getNumber()+"").equals(Nacc)){
                 model.setOwnaccount(a);
                 break;
             }
        }
        String cantidad = request.getParameter("cantidad");
        if((model.getDetalle().equals(""))){
            model.setDetalle("Tranferencia a "+model.getAccount().getClient().getName());
        }
        try{
            Float cant= Float.parseFloat(cantidad);
            model.setPropio(cant);
            if(cant<=0){
                throw new Exception("cantidad minima es 1");
            }
            if(cant>model.getOwnaccount().getBalance()){
                throw  new Exception("Saldo insuficiente");
            }
            session.setAttribute("model", model);
        }catch(Exception e){
            session.setAttribute("msg", e.getMessage());
            return false;
        }
        return true;
    }

    

        
    private String transfer(HttpServletRequest request) {
        if(comprobar(request)){
            return this.transferAction(request);
        }
        return "/presentation/Error.jsp";
    }
    
    
    private String transferAction(HttpServletRequest request) {
      HttpSession session = request.getSession(true);
      FavoritosModel model = (FavoritosModel) session.getAttribute("model");
      try{
          float base = Banco.Logic.Model.instance().Dinerotransferencias(model.getOwnaccount());
          if(model.getOwnaccount().getLimit() >= base+model.getPropio()){
          model.setNuevo(Banco.Logic.Model.instance().ConversorMonedas(model.getOwnaccount().getCurrency().getCurrencyCode(), model.getAccount().getCurrency().getCurrencyCode(), model.getPropio()));
          model.getOwnaccount().setBalance(model.getOwnaccount().getBalance()- model.getPropio());
          model.getAccount().setBalance(model.getAccount().getBalance()+ model.getNuevo());
          Banco.Logic.Model.instance().newTransfer(model.getOwnaccount(), model.getAccount(),model.getPropio(), model.getNuevo(),model.getDetalle());
          return "/presentation/Menu/Cuenta/show";
          }else{
              throw new Exception("Su cuenta #"+model.getOwnaccount().getNumber()+" excede la cantidad maxima de tranferencia por dia -> "+model.getOwnaccount().getLimit());
          }
      }catch(Exception ex){
          model.getOwnaccount().setBalance(model.getOwnaccount().getBalance()+ model.getPropio());
          model.getAccount().setBalance(model.getAccount().getBalance()- model.getNuevo());
          session.setAttribute("msg", ex.getMessage());
          return "/presentation/Error.jsp";
      }
    }
    
    
    public boolean comprobacionrapida(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        FavoritosModel model= new FavoritosModel();
        try{
            model.setNumber(Integer.parseInt((String) request.getParameter("number")));
            session.setAttribute("model", model);
        }catch(Exception ex){
           session.setAttribute("msg", ex.getMessage());
           return false;
        }
        return true;
    }

    
     private String eliminaAction(HttpServletRequest request){
       HttpSession session = request.getSession(true);
       FavoritosModel model= (FavoritosModel) session.getAttribute("model");
       List<Account> lista= (List<Account>) session.getAttribute("favorites");
       for(Account a:lista){
           if(a.getNumber()==model.getNumber()){
               model.setAccount(a);
               break;
           }
       }
       session.setAttribute("model", model);
       return "/presentation/Menu/Cuenta/Favoritos/Eliminar.jsp";
     }
    
    private String elimina(HttpServletRequest request) {
        if(this.comprobacionrapida(request)){
            return this.eliminaAction(request);
        }
        return "/presentation/Error.jsp";
    }

    private String eok(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        FavoritosModel model= (FavoritosModel) session.getAttribute("model");
        Client cl= (Client) session.getAttribute("client");
        try{
            Banco.Logic.Model.instance().EliminarFavorito(cl, model.getAccount());
        }catch(Exception ex){
             session.setAttribute("msg", ex.getMessage());
          return "/presentation/Error.jsp";
        }
        return "/presentation/Menu/Cuenta/show";
    }

     private String añadeAction(HttpServletRequest request) {
       HttpSession session = request.getSession(true);
       FavoritosModel model= (FavoritosModel) session.getAttribute("model");
       List<Account> lista= (List<Account>) session.getAttribute("cuentas");
       try{
        for(Account a:lista){
           if(a.getNumber()==model.getNumber()){
               throw new Exception("La cuenta ya es perteneciente al usuario");
           }
        }
           model.setAccount(Banco.Logic.Model.instance().getCuenta(model.getNumber()+""));
           session.setAttribute("model", model);
       }catch(Exception ex){
          session.setAttribute("msg", ex.getMessage());
          return "/presentation/Error.jsp";
       }
       return "/presentation/Menu/Cuenta/Favoritos/añadir.jsp";
     }
    
    private String añade(HttpServletRequest request) {
        if(this.comprobacionrapida(request)){
            return this.añadeAction(request);
        }
        return "/presentation/Error.jsp";
    }

    private String aok(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        FavoritosModel model= (FavoritosModel) session.getAttribute("model");
        Client cl= (Client) session.getAttribute("client");
        try{
            Banco.Logic.Model.instance().AñadirFavorito(cl, model.getAccount());
        }catch(Exception ex){
          session.setAttribute("msg", ex.getMessage());
          return "/presentation/Error.jsp";
        }
        return "/presentation/Menu/Cuenta/show";
    }
    

}
