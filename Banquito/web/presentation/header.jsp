<%@page import="Banco.Logic.User"%>
<% User usuario= (User) session.getAttribute("usuario"); %>

<header>
    <div class="logo">
        <% if(usuario==null){ %>
            <a href="/Banquito/presentation/index.jsp"><img src="/Banquito/imagenes/Logo.png" width="100" height="98"></a>
        <%}%>
        <% if(usuario!=null){ %>
             <a href="/Banquito/presentation/Menu/show"><img src="/Banquito/imagenes/Logo.png" width="100" height="98"></a>
        <%}%>
        <span>Banco Banquito</span><img src="/Banquito/imagenes/Targeta.png" align="right" width="170" height="110" >
        <center>Sientese seguro!</center>
    </div> 
    <div class="menu">
        <ul>
            <li>
                <% if(usuario==null){ %>
                <a href="/Banquito/presentation/index.jsp">Inicio</a>
                <%}%>
                <% if(usuario!=null){ %>
                 <a href="/Banquito/presentation/Menu/show">Inicio</a>
               <% } %>
            </li>
            <li>
                <a href="/Banquito/presentation/index.jsp">Servicios</a>
            </li>
            <li>
                <a href="/Banquito/presentation/index.jsp">Soporte</a>
            </li>
            <li>
                <a href="/Banquito/presentation/index.jsp">Qos</a>
            </li>
            <li>
                <a href="/Banquito/presentation/index.jsp">About</a>
            </li>
            
                <% if(usuario==null){ %>
            <li>
                <a href="/Banquito/presentation/login/show">Log in</a>
            </li>
            <% } %>
                <% if(usuario!=null){ %>
            <li>
                <%= usuario.toString()%>
            </li>
            <% } %>
                <% if(usuario!=null){ %>
            <li>
                <a href="/Banquito/presentation/login/logout"> Log out</a>
            </li>
            <% } %>
        </ul>
    </div>
  </header>          

