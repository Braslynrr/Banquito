/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Login;
import Banco.Data.UserDao;
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
@WebServlet(name = "LoginController", urlPatterns = {"/presentation/login/login"})
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("model", new Model());
        String viewUrl="";
         switch(request.getServletPath()){  
            case "/presentation/login/login":
                viewUrl=this.login(request);
                break;            
        }
        request.getRequestDispatcher(viewUrl).forward( request, response); 
    }
    
     private String login(HttpServletRequest request) { 
        try{
            Map<String,String> errores =  this.validar(request);
            if(errores.isEmpty()){
                this.updateModel(request);          
                return this.loginAction(request);
            }
            else{
                request.setAttribute("errores", errores);
                return "/presentation/login/View.jsp"; 
            }            
        }
        catch(Exception e){
            return "/presentation/Error.jsp";             
        }         
    }

    void updateModel(HttpServletRequest request){
       Model model= (Model) request.getAttribute("model");       
       //model.getCurrent().setCedula(request.getParameter("cedulaFld"));
       //model.getCurrent().setClave(request.getParameter("claveFld"));
   }
    
    
     Map<String,String> validar(HttpServletRequest request){
        Map<String,String> errores = new HashMap<>();
        if (request.getParameter("cedulaFld").isEmpty()){
            errores.put("cedulaFld","Cedula requerida");
        }

        if (request.getParameter("claveFld").isEmpty()){
            errores.put("claveFld","Clave requerida");
        }
        return errores;
    }
     
        public String loginAction(HttpServletRequest request) {
        Model model= (Model) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        try {
            User real =(User) new UserDao().getUser("11");
            session.setAttribute("usuario", real);
            String viewUrl="";
            return viewUrl;
        } catch (Exception ex) {
            Map<String,String> errores = new HashMap<>();
            request.setAttribute("errores", errores);
            errores.put("cedulaFld","Usuario o clave incorrectos");
            errores.put("claveFld","Usuario o clave incorrectos");
            return "/presentation/login/View.jsp"; 
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
