/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Transacciones.Transaccion;

import Banco.Logic.Account;
import Banco.Logic.Client;
import Banco.Logic.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "TransaccionController", urlPatterns = {"/presentation/Menu/Cajero/Transacciones/BuscarCliente/show",
"/presentation/Menu/Cajero/Transacciones/BuscarCliente/makeTransfer","/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta", "/presentation/Menu/Cajero/Transacciones/BuscarCliente/siguiente",
"/presentation/Menu/Cajero/Transacciones/Transaccion/transaccion", "/presentation/Menu/Cajero/Transacciones/BuscarCliente/transfer","/presentation/Menu/Cajero/Transacciones/Transferencia/transferencia"})
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

        String viewUrl = "";

        switch (request.getServletPath()) {

            case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/show":
                viewUrl = this.show(request);
                break;
            case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta":
                viewUrl = this.getAccount(request);
                break;
            case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/siguiente":
                viewUrl = this.goTransaction(request);
                break;
             case "/presentation/Menu/Cajero/Transacciones/Transaccion/transaccion":
                viewUrl = this.makeTransaction(request);
                break;
             case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/makeTransfer":
                viewUrl = this.makeTransfer(request);
                break;
            case "/presentation/Menu/Cajero/Transacciones/BuscarCliente/transfer":
                viewUrl = this.goTransfer(request);
                break;
            case "/presentation/Menu/Cajero/Transacciones/Transferencia/transferencia":
                viewUrl = this.toTransfer(request);
                break;
   
             

        }
        request.getRequestDispatcher(viewUrl).forward(request, response);

    }

    public String show(HttpServletRequest request) {
        
        HttpSession session = request.getSession(true);
        session.setAttribute("action", "siguiente");
        return this.showAction(request);
    }
    
    public String makeTransfer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("action", "transfer");
        return this.showAction(request);
    }
    
    public String getAccount(HttpServletRequest request) {
        Map<String, String> errores = this.validarId(request);
        if (errores.isEmpty()) {
            return this.getAccountAction(request);
        } else {
            request.setAttribute("errores", errores);
            return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }
    }

    public String goTransaction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        if (model != null) {
            return this.goTransactionAction(request);
        }

        Map<String, String> errores = this.validarCuenta(request);
        if (errores.isEmpty()) {
            return this.goTransactionAction(request);
        } else {
            request.setAttribute("errores", errores);
            return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }

    }
    
      public String goTransfer(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        if (model != null) {
            return this.goTransferAction(request);
        }

        Map<String, String> errores = this.validarCuenta(request);
        if (errores.isEmpty()) {
            return this.goTransferAction(request);
        } else {
            request.setAttribute("errores", errores);
            return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        }

    }
    
     public String makeTransaction(HttpServletRequest request) {

       

        Map<String, String> errores = this.validar(request);
        if (errores.isEmpty()) {
            return this.makeTransactionAction(request);
        } else {
            request.setAttribute("errores", errores);
            return "/presentation/Menu/Cajero/Transacciones/Transaccion.jsp";
        }

    }
     
     public String toTransfer(HttpServletRequest request) {

      
        Map<String, String> errores = this.validar(request);
        if (errores.isEmpty()) {
            return this.toTransferAction(request);
        } else {
            request.setAttribute("errores", errores);
            return "/presentation/Menu/Cajero/Transacciones/Transferencia.jsp";
        }

    }
    public String showAction(HttpServletRequest request) {

        try {
            HttpSession session = request.getSession(true);
            session.removeAttribute("id");
            session.removeAttribute("accounts");
            session.removeAttribute("model");
            //session.invalidate();
            return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        } catch (Exception ex) {
            return "/presentation/Error.jsp";
        }

    }

    public String getAccountAction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = new TransaccionModel();
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
        Client cliente = new Client();

        try {
            cliente = domainModel.compClient((String) request.getParameter("clientid"));
            model.setCuentas(domainModel.ConsultarCuentas(cliente.getCod()));
            model.setCliente(cliente);
            session.setAttribute("accounts", model.getCuentas());
            session.setAttribute("id", request.getParameter("clientid"));
            session.setAttribute("model", model);

            return "/presentation/Menu/Cajero/Transacciones/BuscarCliente.jsp";
        } catch (Exception ex) {
            return "/presentation/Error.jsp";
        }

    }
    
    
     public String makeTransactionAction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
        Transaction transaccion = new Transaction();
        

        try {
           if(request.getParameter("radiobt1").equals("retiro")){
            
               model.getCuenta().setBalance(model.getCuenta().getBalance()- Integer.parseInt(request.getParameter("monto")));
               domainModel.updateAccount(model.getCuenta());
               transaccion.setNumber(domainModel.TransactionNumber());
               transaccion.setType("Retiro");
               transaccion.setAmount(Float.parseFloat(request.getParameter("monto")));
               transaccion.setDate(new Date());
               transaccion.setCurrencyCode((String)model.getCuenta().getCurrency().getCurrencyCode());
               transaccion.setDetail((String)request.getParameter("detail"));
               transaccion.setAccount(model.getCuenta());
               domainModel.addTransaction(transaccion);
        
           }
           
           else{
               
               model.getCuenta().setBalance(model.getCuenta().getBalance() + Integer.parseInt(request.getParameter("monto")));
               domainModel.updateAccount(model.getCuenta());
               domainModel.updateAccount(model.getCuenta());
               transaccion.setNumber(domainModel.TransactionNumber());
               transaccion.setType("Deposito");
               transaccion.setAmount(Float.parseFloat(request.getParameter("monto")));
               transaccion.setDate(new Date());
               transaccion.setCurrencyCode((String) model.getCuenta().getCurrency().getCurrencyCode());
               transaccion.setDetail((String) request.getParameter("detail"));
               transaccion.setAccount(model.getCuenta());
               domainModel.addTransaction(transaccion);

           }

            return "/presentation/Menu/Cajero/Cajero.jsp";
        } catch (Exception ex) {
            return "/presentation/Error.jsp";
        }

    }
    
    public String toTransferAction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();
        Account cuentaDestino;
        Account cuentaOrigen;
        float montoOrigen = 0;
        float montoDestino = montoOrigen;
        String detail;
        
        
        
        
        try{
            cuentaDestino = domainModel.getCuenta(request.getParameter("numcuenta"));
            cuentaOrigen = model.getCuenta();
            montoOrigen = Float.parseFloat(request.getParameter("monto"));
            detail = request.getParameter("detail");
            if(cuentaDestino.getCurrency() != cuentaOrigen.getCurrency()){
                montoDestino = domainModel.ConversorMonedas(cuentaOrigen.getCurrency().getCurrencyCode(), cuentaDestino.getCurrency().getCurrencyCode(), montoOrigen);
                
            }
            cuentaOrigen.setBalance(cuentaOrigen.getBalance()-montoOrigen);
            cuentaDestino.setBalance(cuentaDestino.getBalance()+montoDestino);
            domainModel.newTransfer(cuentaOrigen, cuentaDestino, montoOrigen, montoDestino, detail);
            
            return "/presentation/Menu/Cajero/Cajero.jsp";
        } catch (Exception ex) {
            return "/presentation/Error.jsp";
        }

    }
    

    public String goTransactionAction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();

        if (model != null) {

            try {

                model.setCuenta(domainModel.getCuenta(request.getParameter("accounts")));
                model.setTipomoneda(model.getCuenta().getCurrency().getDescription());
            } catch (Exception ex) {
                Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("model", model);
            return "/presentation/Menu/Cajero/Transacciones/Transaccion.jsp";
        } else {

            try {

                model = new TransaccionModel();
                model.setCuenta(domainModel.getCuenta((String) request.getParameter("accountnumber")));
                model.setCliente(domainModel.getByCod(model.getCuenta().getClient().getCod()));
                model.setTipomoneda(model.getCuenta().getCurrency().getDescription());
                session.setAttribute("model", model);
                return "/presentation/Menu/Cajero/Transacciones/Transaccion.jsp";
            } catch (Exception ex) {
                Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return "/presentation/Error.jsp";
    }
    
        public String goTransferAction(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        TransaccionModel model = (TransaccionModel) session.getAttribute("model");
        Banco.Logic.Model domainModel = Banco.Logic.Model.instance();

        if (model != null) {

            try {

                model.setCuenta(domainModel.getCuenta(request.getParameter("accounts")));
                model.setTipomoneda(model.getCuenta().getCurrency().getDescription());
            } catch (Exception ex) {
                Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("model", model);
            return "/presentation/Menu/Cajero/Transacciones/Transferencia.jsp";
        } else {

            try {

                model = new TransaccionModel();
                model.setCuenta(domainModel.getCuenta((String) request.getParameter("accountnumber")));
                model.setCliente(domainModel.getByCod(model.getCuenta().getClient().getCod()));
                model.setTipomoneda(model.getCuenta().getCurrency().getDescription());
                session.setAttribute("model", model);
                return "/presentation/Menu/Cajero/Transacciones/Transferencia.jsp";
            } catch (Exception ex) {
                Logger.getLogger(TransaccionController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return "/presentation/Error.jsp";
    }
    

    Map<String, String> validarId(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();

        if (request.getParameter("clientid").isEmpty()) {
            errores.put("clientid", "Espacio requerido");
        }
        return errores;
    }

    Map<String, String> validarCuenta(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();

        if (request.getParameter("accountnumber").isEmpty()) {
            errores.put("accountnumber", "Espacio requerido");
        }
        return errores;
    }
    
     Map<String, String> validar(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        HttpSession session = request.getSession(true);
        TransaccionModel model= (TransaccionModel) session.getAttribute("model");

        if (request.getParameter("detail").isEmpty()) {
            errores.put("detail", "Espacio requerido");
        }
        if (request.getParameter("monto").isEmpty()) {
            errores.put("monto", "Espacio requerido");
        }
        if (Float.parseFloat(request.getParameter("monto"))>model.getCuenta().getBalance())
        {
            errores.put("monto", "Fondos insuficientes");
        
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
