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
@WebServlet(name = "LoginController", urlPatterns = {"/presentation/login/show"})
public class Controller extends HttpServlet {

    /*
        
        Procesa el request, depende del url recibido
    
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("model", new Model());
        String viewUrl="";
         switch(request.getServletPath()){  
             
            case "/presentation/login/show":
                viewUrl=this.show(request);
                break;
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
                return "/presentation/login/Login.jsp"; 
            }            
        }
        catch(Exception e){
            return "/presentation/Error.jsp";             
        }         
    }
     
    Map<String,String> validar(HttpServletRequest request){
        Map<String,String> errores = new HashMap<>();
        if (request.getParameter("userid").isEmpty()){
            errores.put("userid","Cedula requerida");
        }

        if (request.getParameter("userpass").isEmpty()){
            errores.put("userpass","Clave requerida");
        }
        return errores;
    }
    void updateModel(HttpServletRequest request){
       Model model= (Model) request.getAttribute("model");   
       //Se supone que aqui se obtienen los datos 
       model.getCurrent().setId(request.getParameter("id"));
       model.getCurrent().setPassword(request.getParameter("password"));
   }
    
    

    public String loginAction(HttpServletRequest request) {
        Model model= (Model) request.getAttribute("model");
        Banco.Logic.Model  domainModel = Banco.Logic.Model.instance();
        HttpSession session = request.getSession(true);
        try {
            //User real =(User) new UserDao().getUser("11");
            User real = domainModel.consultUser(model.getCurrent().getId());
            session.setAttribute("usuario", real);
            String viewUrl="";
            return viewUrl;
        } catch (Exception ex) {
            Map<String,String> errores = new HashMap<>();
            request.setAttribute("errores", errores);
            errores.put("userid","Usuario o clave incorrectos");
            errores.put("userpass","Usuario o clave incorrectos");
            return "/presentation/login/Login.jsp"; 
        }        
    } 
    
     public String logout(HttpServletRequest request){
        return this.logoutAction(request);
    }
    
    public String logoutAction(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.removeAttribute("usuario");
        session.invalidate();
        return "/presentation/index.jsp";   
    }

    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    
    //Muestra el view del mvc del login
    public String showAction(HttpServletRequest request){
        Model model= (Model) request.getAttribute("model");
        model.getCurrent().setId("");
        model.getCurrent().setPassword("");
        return "/presentation/login/Login.jsp"; 
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
