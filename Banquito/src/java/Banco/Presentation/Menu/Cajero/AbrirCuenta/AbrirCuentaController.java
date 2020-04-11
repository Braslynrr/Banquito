/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.AbrirCuenta;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AbrirCuentaController", urlPatterns = {"/presentation/Menu/Cajero/AbrirCuenta/show"})
public class AbrirCuentaController extends HttpServlet {

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
       
        request.setAttribute("model", new AbrirCuentaModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/AbrirCuenta/show":
               viewUrl=this.show(request);
            break;     

            
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
        

    }
      public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    
     public String showAction(HttpServletRequest request){
        AbrirCuentaModel model= (AbrirCuentaModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
    
        try{
           this.updateModel(request);
           model.setMonedas(Banco.Logic.Model.instance().Consultarcurrency());
           
           //session.setAttribute("password", Banco.Logic.Model.instance().getpassword());
           session.setAttribute("currencies", model.getMonedas());
           return "/presentation/Menu/Cajero/AbrirCuenta.jsp";
        }catch(Exception ex){
            return "/presentation/Menu/Cajero/Registro.jsp"; 
        }
        
       
      
    
    }
       void updateModel(HttpServletRequest request){
       AbrirCuentaModel model= (AbrirCuentaModel) request.getAttribute("model");
       HttpSession session = request.getSession(true);
     
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
