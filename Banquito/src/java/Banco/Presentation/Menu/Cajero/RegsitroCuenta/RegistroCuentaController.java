/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.RegsitroCuenta;

import Banco.Logic.Account;
import Banco.Presentation.Menu.Cajero.Registro.RegistroController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gaira
 */
@WebServlet(name = "RegistroCuentaController", urlPatterns = {"/presentation/Menu/Cajero/RegistroCuenta/show","/presentation/Menu/Cajero/RegistroCuenta/registrar"})
public class RegistroCuentaController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        request.setAttribute("model", new RegistroCuentaModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/RegistroCuenta/show":
               viewUrl=this.show(request);
            break;     
        case "/presentation/Menu/Cajero/RegistroCuenta/registrar":
               viewUrl=this.registrar(request);
            break;    
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
        
        
        
        
        
        
    }
    
    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    public String registrar(HttpServletRequest request){
        
        try{ 
            
                    
                return this.registrarAction(request);
          
       
            
        }
           catch(Exception e){
            return "/presentation/Error.jsp";             
        }   
    }
    
    public  String registrarAction (HttpServletRequest request) {
         
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
        RegistroCuentaModel model = (RegistroCuentaModel) request.getAttribute("model");
        Account cuenta = new Account();
        HttpSession session = request.getSession(true);

        try {
            model.setClient(Banco.Logic.Model.instance().ConsutClient((String) session.getAttribute("id")));
            cuenta.setBalance(new Float(0));
            cuenta.setClient(model.getClient());
            cuenta.setNumber(domainModel.accountNumber());
            cuenta.setCurrency(domainModel.getCurrency(request.getParameter("currency")));
            cuenta.setLimit(Double.valueOf(request.getParameter("limit")));
            domainModel.addAccount(cuenta);

            return "/presentation/Menu/Cajero/RegistroCuenta.jsp";
        }
         catch (Exception ex) {
            
 
             return "/presentation/Error.jsp"; 
        }    
     }
    
    public String showAction(HttpServletRequest request){
        RegistroCuentaModel model= (RegistroCuentaModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        
        
    
        try{
           this.updateModel(request);
           model.setMonedas(Banco.Logic.Model.instance().Consultarcurrency());
           model.setClient(Banco.Logic.Model.instance().ConsutClient((String)session.getAttribute("id")));
           //session.setAttribute("password", Banco.Logic.Model.instance().getpassword());
           session.setAttribute("currencies", model.getMonedas());
           session.setAttribute("id", model.getClient().getUser().getId());
           session.setAttribute("username", model.getClient().getName());
           session.setAttribute("userphone", model.getClient().getTelNumber());
           return "/presentation/Menu/Cajero/RegistroCuenta.jsp";
        }catch(Exception ex){
            return "/presentation/Error.jsp"; 
        }
        
       
      
    
    }
    void updateModel(HttpServletRequest request){
       RegistroCuentaModel model= (RegistroCuentaModel) request.getAttribute("model");
       HttpSession session = request.getSession(true);
     
   }

    
   /*Map<String,String> validar(HttpServletRequest request) {
        Map<String,String> errores = new HashMap<>();
        Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
        
        try {
            if (domainModel.clientExist(request.getParameter("userid"))){
                errores.put("userid","El cliente ya se encuentra registrado"); 
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroCuentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (request.getParameter("userid").isEmpty()){
            errores.put("userid","Cedula requerida");
            
        }

        if (request.getParameter("userpass").isEmpty()){
            errores.put("userpass","Clave requerida");
        }
        if (request.getParameter("username").isEmpty()){
            errores.put("username","Espacio requerido");
        }

        if (request.getParameter("tnumber").isEmpty()){
            errores.put("tnumber","Espacio requerido");
        }
        return errores;
    }*/
    
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

}
