<%@page import="Banco.Logic.User"%>
<% User usuario= null; %>

<header>
    <div class="logo">
        <img src="\Banquito\web\Imagenes\Logo2.png">
        <span>Banco Banquito</span>
    </div> 
    <div class="menu">
        <ul> 
              <li>
                <a href="/Guia/presentation/Index.jsp">Inicio</a>
              </li>
                        <% if (usuario!=null){ %>
                <li>
                  <a href="/Guia/presentation/cliente/cuentas/show">Cuentas</a>
                  <ul>  <!--submenu --> </ul>
                </li>                        
                <li >
                  <a  href="/Guia/presentation/login/logout">Logout</a>
                  <ul>  <!--submenu --> </ul>
                </li>                
                        <% } %>
                        <% if (usuario==null){%>
                <li>
                  <a href="/Guia/presentation/login/show">Login</a>
                </li>
                
                        <% }%>             
            </ul>
    </div>
  </header>          

