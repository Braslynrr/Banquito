/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero;



import Banco.Logic.Cashier;
import Banco.Logic.User;
import Banco.Presentation.Menu.Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "CajeroController", urlPatterns = {"/presentation/Menu/Cajero/show","/presentation/Menu/Cajero/Intereses","/presentation/Menu/Cajero/Aplicar"})
public class CajeroController extends HttpServlet {
    
    
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.setAttribute("model", new CajeroModel());
        String viewUrl="";
         switch(request.getServletPath()){  
             
        case "/presentation/Menu/Cajero/show":
               viewUrl=this.show(request);
            break;            
        case "/presentation/Menu/Cajero/Intereses":
                viewUrl=this.intereses(request);
            break;
        case "/presentation/Menu/Cajero/Aplicar":
                viewUrl=this.Aplicar(request);
            break;
        }
        request.getRequestDispatcher(viewUrl).forward( request, response);
    }

    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    
    
        public String showAction(HttpServletRequest request){
        CajeroModel model= (CajeroModel) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        Cashier cashier = (Cashier) session.getAttribute("cashier");
        model.setCashier(cashier);
        try{
          // model.setCuentas(Banco.Logic.Model.instance().ConsultarCuentas(cliente.getId(),cliente.getUser().getId()));
           //session.setAttribute("cuentas", model.getCuentas());
           return "/presentation/Menu/Cajero/Cajero.jsp";
        }catch(Exception ex){
            
        }
       
        return "/presentation/Menu/Cajero/Cajero.jsp"; 
    } 
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

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

    private String intereses(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        try{
            CajeroModel model= new CajeroModel();
            model.setInteres(Banco.Logic.Model.instance().FechaInteres());
            session.setAttribute("model",model);
            session.setAttribute("hoy",new Date());
            return "/presentation/Menu/Cajero/Intereses.jsp";
        }catch(Exception ex){
            session.setAttribute("msg", ex.getMessage());
            return "/presentation/Error.jsp";
        }
    }

    private String Aplicar(HttpServletRequest request) {
         HttpSession session = request.getSession(true);
         CajeroModel model =(CajeroModel) session.getAttribute("model");
         try{
             Banco.Logic.Model.instance().InteresMasivo();
             Banco.Logic.Model.instance().setServerDate(new Date(model.getInteres().getTime()+31*100*60*60*24));
             return "/presentation/Menu/Cajero/show";
         }catch(Exception ex){
             session.setAttribute("msg", ex.getMessage());
             return "/presentation/Error.jsp";
         }
    }

}
