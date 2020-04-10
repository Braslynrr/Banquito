<%@page import="java.util.List"%>
<%@page import="Banco.Logic.Cashier"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <link href="/Banquito/css/cashiermenu.css" rel="stylesheet" type="text/css" />
         <% session.getAttribute("cashier");%>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        <%@page import="Banco.Logic.Client"%>
        <%@page import="Banco.Logic.Cashier"%>
       
         <% Cashier cashier=(Cashier) session.getAttribute("cashier"); %>
        <div class="box">
            <h1>Menu cajeros</hl>
            <div id="menu">   
                <input type="submit" value="Registrar un cliente" onclick="Redirect1();" >
                <input type="submit" value="Abrir una cuenta" onclick="Redirect1();" >
            </div>
             <h2>Codigo de cajero: <%=cashier.getCod()%></h2>
        </div>
    
      <script type = "text/javascript">
         <!--
            function Redirect1() {
               window.location = "/Banquito/presentation/Menu/Cajero/Registro/show";
            }
            
        
      </script>   
        
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>

