/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Registro;

import Banco.Logic.Account;
import Banco.Logic.Client;
import Banco.Logic.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
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
@WebServlet(name = "RegistroController", urlPatterns = {"/presentation/Menu/Cajero/Registro/show","/presentation/Menu/Cajero/Registro/registrar"})
public class RegistroController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.setAttribute("model", new RegistroModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/Registro/show":
               viewUrl=this.show(request);
            break;     
        case "/presentation/Menu/Cajero/Registro/registrar":
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
            Map<String,String> errores =  this.validar(request);
            if(errores.isEmpty()){         
                return this.registrarAction(request);
            }
            else{
                request.setAttribute("errores", errores);
                return "/presentation/Menu/Cajero/Registro.jsp"; 
            }      
            
        }
           catch(Exception e){
            return "/presentation/Error.jsp";             
        }   
    }
    
     public  String registrarAction (HttpServletRequest request) {
         
         Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
         User real = new User();
         Client cliente = new Client();
         Account cuenta = new Account();
         
         try{
             
         real.setId(request.getParameter("userid"));
         real.setPassword(request.getParameter("userpass"));
         cliente.setCod(domainModel.clientCode());
         cliente.setName(request.getParameter("username"));
         cliente.setTelNumber(request.getParameter("tnumber"));
         
         cliente.setUser(real);
         cuenta.setBalance(new Float(0));
         cuenta.setClient(cliente);
         cuenta.setNumber(domainModel.accountNumber());
         cuenta.setCurrency(domainModel.getCurrency(request.getParameter("currency")));
         cuenta.setLimit(Double.valueOf(request.getParameter("limit")));
         
         domainModel.addUser(real);
         domainModel.addClient(cliente);
         domainModel.addAccount(cuenta);
         return "/presentation/Menu/Cajero/Registro.jsp";
         }
         catch (Exception ex) {
            
 
             return "/presentation/Error.jsp"; 
        }    
     }
    
    public String showAction(HttpServletRequest request){
        RegistroModel model= (RegistroModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
    
        try{
           this.updateModel(request);
           model.setMonedas(Banco.Logic.Model.instance().Consultarcurrency());
           
           session.setAttribute("password", Banco.Logic.Model.instance().getpassword());
           session.setAttribute("currencies", model.getMonedas());
           session.setAttribute("userid", session.getAttribute("id"));
           return "/presentation/Menu/Cajero/Registro.jsp";
        }catch(Exception ex){
            return "/presentation/Menu/Cajero/Registro.jsp"; 
        }
        
       
      
    
    }
    void updateModel(HttpServletRequest request){
       RegistroModel model= (RegistroModel) request.getAttribute("model");
       HttpSession session = request.getSession(true);
       
     
   }

    
   Map<String,String> validar(HttpServletRequest request) {
        Map<String,String> errores = new HashMap<>();
        Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
        
        try {
            if (domainModel.clientExist(request.getParameter("userid"))){
                errores.put("userid","El cliente ya se encuentra registrado"); 
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
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

}
