
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
       
        <% RegistroModel model= (RegistroModel) request.getAttribute("model"); %>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
      
        
      <div class="box">
        <form  name="form" action = "/Banquito/presentation/Menu/Cajero/Registro/registrar" method="post">
            
            <h1>Ingrese los datos del nuevo cliente</hl>    
  
            <input type = "text" name = "username" placeholder="Nombre" class = "<%=erroneo("username",errores)%>" title="<%=title("username",errores)%>" />
            <input type = "text" name = "tnumber" placeholder="Numero de telefono" class = "<%=erroneo("tnumber",errores)%>" title="<%=title("tnumber",errores)%>" />
            <input type = "text" name = "userid" placeholder="id" class = "<%=erroneo("userid",errores)%>" title="<%=title("userid",errores)%>"/>
            <input type = "text" name = "userpass" placeholder="contrasena" class = "<%=erroneo("userpass",errores)%>" title="<%=title("userpass",errores)%>"/>
            
            <input type = "submit" value = "Registrar">
       
        
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