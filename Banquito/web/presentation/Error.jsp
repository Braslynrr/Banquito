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
        <input type="submit" value="Volver al inicio" onclick="Inicio(usuario)">
    </div>  
</body>
</html>

<script>    
    
    function Inicio(usuario){
       
     if(usuario!==null){
        window.location = "/Banquito/presentation/Menu/show";  
     }else{
        window.location = "/Banquito/presentation/Menu/index.jsp";  
     }
    }
</script>