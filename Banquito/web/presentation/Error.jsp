<%@page import="Banco.Logic.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="/presentation/Head.jsp" %>
 <link href="/Banquito/css/Error.css" rel="stylesheet" type="text/css" />
 <title>Error</title> 
 <% String msg= (String) session.getAttribute("msg");%>
</head>
<body>
    <%@ include file="/presentation/header.jsp" %>
    <div class="derror">
        <h1>Ha ocurrido un error <br>
        <%= msg %></h1>
        <% if(usuario!=null){%>
        <input type="submit" value="Volver al inicio" onclick="Inicio(0)">
        <% } %>
         <% if(usuario==null){%>
        <input type="submit" value="Volver al inicio" onclick="Inicio(1)">
        <% } %>
    </div>  
</body>
</html>

<script>    
    
    function Inicio(usuario){
       
     if(usuario===0){
        window.location = "/Banquito/presentation/Menu/show";  
     }else{
        window.location = "/Banquito/presentation/Menu/index.jsp";  
     }
    }
</script>