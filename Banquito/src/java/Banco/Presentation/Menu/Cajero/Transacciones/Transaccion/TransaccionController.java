/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Transacciones.Transaccion;

import Banco.Logic.Client;
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
@WebServlet(name = "TransaccionController", urlPatterns = {"/presentation/Menu/Cajero/Transacciones/BuscarCliente/show",
"/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta"})
public class TransaccionController extends HttpServlet {

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
 
        request.setAttribute("model", new TransaccionModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/show":
               viewUrl=this.show(request);
            break;
        case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta":
               viewUrl=this.getAccount(request);
            break;   
       
        }
        request.getRequestDispatcher(viewUrl).forward( request, response); 
        
    }
    
    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
     public String getAccount(HttpServletRequest request){
        return this.getAccountAction(request);
    }
    public String showAction(HttpServletRequest request){
        
       TransaccionModel model= (TransaccionModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
      
          try{
           //this.updateModel(request);
           return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }catch(Exception ex){
            return "/presentation/Error.jsp"; 
        }
    
    }
     public String getAccountAction(HttpServletRequest request){
        
       TransaccionModel model= (TransaccionModel) request.getAttribute("model");
       HttpSession session = request.getSession(true);
       Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
       Client cliente = new Client();
       
      
          try{
           cliente = domainModel.compClient((String)request.getParameter("clientid"));
           model.setCuentas(domainModel.ConsultarCuentas(cliente.getCod()));
           session.setAttribute("accounts", model.getCuentas());
           session.setAttribute("id", request.getParameter("clientid"));
           return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }catch(Exception ex){
            return "/presentation/Error.jsp"; 
        }
    
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
