/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu;

import Banco.Logic.Client;
import Banco.Logic.User;
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
 * @author Brazza
 */
@WebServlet(name = "Controller", urlPatterns = {"/presentation/Menu/show"})
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    request.setAttribute("model", new Model());
        String viewUrl="";
         switch(request.getServletPath()){  
             
        case "/presentation/Menu/show":
               viewUrl=this.show(request);
            break;
       //     case "/presentation/login/login":
        //        viewUrl=this.login(request);
         //       break;            
        }
        request.getRequestDispatcher(viewUrl).forward( request, response); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String show(HttpServletRequest request){
        return this.showAction(request);
    }
    
    public String showAction(HttpServletRequest request){
        Model model= (Model) request.getAttribute("model");
        HttpSession session = request.getSession(true);
        User usuario = (User) session.getAttribute("usuario");
        model.setUsuario(usuario);
        try{
            model.setCliente(Banco.Logic.Model.instance().ConsutClient(usuario.getId()));
            model.setCash(Banco.Logic.Model.instance().consultcash(usuario.getId()));
        }catch(Exception ex){
            
        }
       
        return "/presentation/Menu/Menu.jsp"; 
    }   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
