/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Transacciones.Transaccion;

import Banco.Logic.Client;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "TransaccionController", urlPatterns = {"/presentation/Menu/Cajero/Transacciones/BuscarCliente/show",
"/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta","/presentation/Menu/Cajero/Transacciones/BuscarCliente/siguiente"})
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
 
        //request.setAttribute("model", new TransaccionModel());
        String viewUrl ="";
        
        switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/show":
               viewUrl=this.show(request);
            break;
        case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta":
               viewUrl=this.getAccount(request);
            break;   
       case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/siguiente":
               viewUrl=this.goTransaction(request);
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
    public String goTransaction(HttpServletRequest request){
        return this.goTransactionAction(request);
    } 
    public String showAction(HttpServletRequest request){
        
       
      
          try{
           //this.updateModel(request);
           return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }catch(Exception ex){
            return "/presentation/Error.jsp"; 
        }
    
    }
     public String getAccountAction(HttpServletRequest request){
        
       
       HttpSession session = request.getSession(true);
       TransaccionModel model= new TransaccionModel();
       Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
       Client cliente = new Client();
       
      
          try{
           cliente = domainModel.compClient((String)request.getParameter("clientid"));
           model.setCuentas(domainModel.ConsultarCuentas(cliente.getCod()));
           model.setCliente(cliente);
           session.setAttribute("accounts", model.getCuentas());
           session.setAttribute("id", request.getParameter("clientid"));
           session.setAttribute("model", model);

           return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }catch(Exception ex){
            return "/presentation/Error.jsp"; 
        }
    
    }
     
       public String goTransactionAction(HttpServletRequest request){
        
       
       HttpSession session = request.getSession(true);
       TransaccionModel model= new TransaccionModel();
       Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
       
       
       if(model.getCliente()!= null){
          
           try {

                   model.setCuenta(domainModel.getCuenta(request.getParameter("accounts")));
               } catch (Exception ex) {
                   Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
               }
               session.setAttribute("model", model);
               return "/presentation/Menu/Cajero/Transacciones/Transaccion.jsp";
           }
       else{
       
           try {
               model.setCuenta(domainModel.getCuenta((String)request.getParameter("accountnumber")));
               model.setCliente(domainModel.getByCod(model.getCuenta().getClient().getCod()));
               session.setAttribute("model", model);
               return "/presentation/Menu/Cajero/Transacciones/Transaccion.jsp";
           } catch (Exception ex) {
               Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
       
       }
       return "/presentation/Error.jsp";
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
