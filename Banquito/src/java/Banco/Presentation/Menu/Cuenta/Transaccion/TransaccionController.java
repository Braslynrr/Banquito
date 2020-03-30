/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Transaccion;

import Banco.Logic.Client;
import Banco.Presentation.Menu.Cuenta.CuentaModel;
import Logic.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "transaccioncontroller", urlPatterns = {"/presentation/Menu/Cuenta/Transacciones/show"})
public class TransaccionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("model", new TransaccionModel());
        String viewUrl="";
         switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cuenta/Transacciones/show":
               viewUrl=this.show(request);
            break;            
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
    }
    
    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    
     public String showAction(HttpServletRequest request){
        TransaccionModel model = ( TransaccionModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        try{
           this.updateModel(request);
           model.setTrans(Banco.Logic.Model.instance().consultarTransaciones(model.getAccount().getNumber(),model.getAccount().getClient().getId()));
           session.setAttribute("transactions", model.getTrans());
           session.setAttribute("account", model.getAccount());
           return "/presentation/Menu/Cuenta/Transacciones/View.jsp";
        }catch(Exception ex){
            session.setAttribute("msg", ex.toString());
            return "/presentation/Error.jsp";
        }
    } 
     
    
    void updateModel(HttpServletRequest request)throws Exception{
        TransaccionModel model= ( TransaccionModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        model.setAccount(Banco.Logic.Model.instance().getCuenta(request.getParameter("numeroFld")));
        Client c= (Client) session.getAttribute("client");
        if(!c.getId().equals(model.getAccount().getClient().getId())){
            throw new Exception("Acceso Ilegal");
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
