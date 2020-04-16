/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.RegistroCajero;

import Banco.Logic.Cashier;
import Banco.Logic.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "RegistroCajeroController", urlPatterns = {"/presentation/Menu/Cajero/RegistroCajero/show","/presentation/Menu/Cajero/RegistroCajero/validate"
,"/presentation/Menu/Cajero/RegistroCajeroCliente/registrar"})
public class RegistroCajeroController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        
        request.setAttribute("model", new RegistroCajeroModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/RegistroCajero/show":
               viewUrl=this.show(request);
            break;     
        case "/presentation/Menu/Cajero/RegistroCajero/validate":
               viewUrl=this.validateId(request);
            break;
        case "/presentation/Menu/Cajero/RegistroCajeroCliente/registrar":
               viewUrl=this.registrar(request);
            break; 
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
    }
    
    public String show(HttpServletRequest request) {
        return this.showAction(request);
    }

    public String showAction(HttpServletRequest request) {
       
        try {
          

            return "/presentation/Menu/Cajero/RegistroCajero.jsp";
        } catch (Exception ex) {
            return "/presentation/Error.jsp";
        }

    }
   public String validateId(HttpServletRequest request){
         
        try{ 
            Map<String,String> errores =  this.validar(request);
            if(errores.isEmpty()){         
                return this.validateIdAction(request);
            }
            else{
                request.setAttribute("errores", errores);
                return "/presentation/Menu/Cajero/RegistroCajero.jsp"; 
            }      
            
        }
           catch(Exception e){
            return "/presentation/Error.jsp";             
        }   
    }
     public  String validateIdAction (HttpServletRequest request) {
         
         Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
         RegistroCajeroModel model= (RegistroCajeroModel) request.getAttribute("model");
         HttpSession session = request.getSession(true);
         try{
             
             if(domainModel.compClient(request.getParameter("cashierid")) != null){
                 model.setCliente(domainModel.compClient(request.getParameter("cashierid")));
                 return "/presentation/Menu/Cajero/RegistroCajeroCliente.jsp";
             }
             else {
                 User user = new User();
                 user.setId(request.getParameter("cashierid"));
                 user.setPassword(domainModel.getpassword());
                 session.setAttribute("user", user);
                 return  "/presentation/Menu/Cajero/RegistroCajeroNuevo.jsp";
                 
             }
         
         
         }
         catch (Exception ex) {
            
 
             return "/presentation/Error.jsp"; 
        }    
     }
     
    public String registrar(HttpServletRequest request){
         
            
                return this.registrarAction(request);
   
    }
     
     public  String registrarAction (HttpServletRequest request) {
         
        Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
       HttpSession session = request.getSession(true);
        Cashier cajero = new Cashier();
         
         try{
           
             if (session.getAttribute("user") == null) {
                 cajero.setCod(domainModel.cashierCode());
                 cajero.setName(request.getParameter("username"));
                 cajero.setUser(domainModel.getUser(request.getParameter("userid")));
             }
             
             else{
                 
                 
                 User user = (User)session.getAttribute("user");
                 cajero.setCod(domainModel.cashierCode());
                 cajero.setName(request.getParameter("username"));
                 cajero.setUser(user);
                 domainModel.addUser(user);
                 
                 
             }
          
          
          domainModel.addCashier(cajero);
 
         return "/presentation/Menu/Cajero/RegistroCajero/show";
         }
         catch (Exception ex) {
            
 
             return "/presentation/Error.jsp"; 
        }    
     }
    
     Map<String,String> validar(HttpServletRequest request) {
        Map<String,String> errores = new HashMap<>();
        if (request.getParameter("cashierid").isEmpty()){
            errores.put("cashierid","Cedula requerida");
            
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
