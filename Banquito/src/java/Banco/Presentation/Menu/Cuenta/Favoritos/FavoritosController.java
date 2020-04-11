/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Favoritos;

import Banco.Logic.Account;
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
@WebServlet(name = "FavoritosController", urlPatterns = {"/presentation/Menu/Cuenta/Favorito/show","/presentation/Menu/Cuenta/Favorito/transfer"})
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
        List<Account> accounts= (List<Account>) session.getAttribute("cuentas");
        for(Account a:accounts){
             if((a.getNumber()+"").equals(Nacc)){
                 model.setOwnaccount(a);
                 break;
             }
        }
        String cantidad = request.getParameter("cantidad");
        try{
            Float cant= Float.parseFloat(cantidad);
            model.setPropio(cant);
            if(cant>model.getOwnaccount().getBalance()){
                throw  new Exception("Saldo insuficiente");
            }
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
          model.setNuevo(Banco.Logic.Model.instance().ConversorMonedas(model.getOwnaccount().getCurrency().getCurrencyCode(), model.getAccount().getCurrency().getCurrencyCode(), model.getPropio()));
          model.getOwnaccount().setBalance(model.getOwnaccount().getBalance()- model.getPropio());
          model.getAccount().setBalance(model.getAccount().getBalance()+ model.getNuevo());
          Banco.Logic.Model.instance().newTransfer(model.getOwnaccount(), model.getAccount() , model.getNuevo());
          return "/presentation/Menu/Cuenta/show";
      }catch(Exception ex){
          model.getOwnaccount().setBalance(model.getOwnaccount().getBalance()+ model.getPropio());
          model.getAccount().setBalance(model.getAccount().getBalance()- model.getNuevo());
          session.setAttribute("msg", ex.getMessage());
          return "/presentation/Error.jsp";
      }
    }
    

}
