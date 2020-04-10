<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/selector.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        <%@page import="Banco.Logic.Client"%>
        <%@page import="Banco.Logic.Cashier"%>
        <% Client cliente=(Client) session.getAttribute("client"); %>
         <% Cashier cashier=(Cashier) session.getAttribute("cashier"); %>
        <div class="box">
            <h1>Cliente:<%=cliente.getCod()%></h1>
            <div id="cuen">
                <input type="submit" value="ir a Cuenta" onclick="Redirect1();">
            </div>
        </div>
        <div class="box3">
            <img src="/Banquito/imagenes/wilaT.png">
        </div>
        <div class="box2">
            <h1>Cajero:<%=cashier.getCod()%> </h1>
            <div id="caj">   
            <input type="submit" value="Ir al Cajero" onclick="Redirect2();" >
            </div>
        </div>
         <script type = "text/javascript">
         <!--
            function Redirect1() {
               window.location = "/Banquito/presentation/Menu/Cuenta/show";
            }
            
            function Redirect2() {
               window.location = "/Banquito/presentation/Menu/Cajero/show";
            }
      </script>
    </body>
    <%@ include file="/presentation/footer.jsp" %>
</html>
