
<%@page import="Banco.Logic.Currency"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Banco.Logic.Cashier"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Banco.Presentation.Menu.Cajero.Registro.RegistroModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <link href="/Banquito/css/registrouser.css" rel="stylesheet" type="text/css" />
         <% session.getAttribute("cashier");%>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
       
       
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
        <% List<Currency> lista=(List<Currency>) session.getAttribute("currencies");%>
        <% String password = (String) session.getAttribute("password"); %>
        <% String id = (String) session.getAttribute("id"); %>
        
      <div class="box">
          <form  name="form" action = "/Banquito/presentation/Menu/Cajero/Registro/registrar" method="post">

              <h1>Ingrese los datos del nuevo cliente</hl>    

                  <input type = "text" name = "username" placeholder="Nombre" class = "<%=erroneo("username", errores)%>" title="<%=title("username", errores)%>" />
                  <input type = "text" name = "tnumber" placeholder="Numero de telefono" class = "<%=erroneo("tnumber", errores)%>" title="<%=title("tnumber", errores)%>" />
                  <h3>ID</h3>  
                  <input type = "text" name = "userid" placeholder="id" value=<%= id %> class = "<%=erroneo("userid", errores)%>" title="<%=title("userid", errores)%>"/>
                  <h3>Contraseña generada</h3>  
                  <input type = "text" name = "userpass" placeholder="contrasena" value=<%= password %> class = "<%=erroneo("userpass", errores)%>" title="<%=title("userpass", errores)%>"/>
                  <input type = "text" name = "limit" placeholder="Limite" class = "<%=erroneo("limit", errores)%>" title="<%=title("limit", errores)%>"/>
                  <h2>Seleccione el tipo de moneda para la nueva cuenta</h2>
                  <select id = "currencyType" name = "currency">

                      <% for (Currency m : lista) {%>
                      <option>  <%= m.getDescription()%> </option>
                     
                      <% } %>
                      <% if (lista.isEmpty()) { %>
                      <option value = "No se han registrado tipos de cambio" </option>
                      <% }%>
                      
                  </select>
                  <input type = "submit" value = "Siguiente">


                  </form>
                  </div>

                  </script>   

                  </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>


<%!
    private String erroneo(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return "is-invalid";
      else
        return "";
    }
    private String title(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return errores.get(campo);
      else
        return "";
    }

  
    
%> 