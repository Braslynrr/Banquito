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
          
                <input type="submit" value="Abrir una cuenta" onclick="Redirect2();" >
                <input type="submit" value="Registrar un cajero" onclick="Redirect3();">
                <input type="submit" value="Realizar un deposito o retiro" onclick="Redirect4();" >
                <input type="submit" value="Aplicar Intereses" onclick="Redirect1();" >
            </div>
             <h2>Codigo de cajero: <%=cashier.getCod()%></h2>
        </div>
    
      <script type = "text/javascript">
         <!--
         
            function Redirect2() {
               window.location = "/Banquito/presentation/Menu/Cajero/AbrirCuenta/show";
            }
             function Redirect1() {
               window.location = "/Banquito/presentation/Menu/Cajero/Intereses";
            }
               function Redirect3() {
               window.location = "/Banquito/presentation/Menu/Cajero/RegistroCajero/show";
             }
             
            function Redirect4() {
               window.location = "/Banquito/presentation/Menu/Cajero/Transacciones/BuscarCliente/show";
             }
      </script>   
        
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>

