<%@page import="Banco.Presentation.Menu.Cuenta.Favoritos.FavoritosModel"%>
<%@page import="java.util.List"%>
<%@page import="Banco.Logic.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/transferF.css" rel="stylesheet" type="text/css" />
        <% List<Account> accounts=(List<Account>) session.getAttribute("cuentas"); %>
        <% FavoritosModel model = (FavoritosModel) session.getAttribute("model"); %>
        <
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        
    <from class="box">
        <h2>Menu de Transferencia Bancaria</h2>
        Cuenta:
        <select id = "selectaccount" name = "Saccounts">

            <% for (Account a : accounts){ %>
            <option id="<%= a.getNumber()%>"  title="<%=a.getBalance()+" "+a.getCurrency().getDescription() %>"> <%= a.getNumber() %>  </option>
            <% } %>
        </select>
            Cantidad:<input id="cant" type="number" min="1"><br>
        tranferir a numero de <%= model.toString()%>
        <br>
        <input type="button" value="Cancelar" onclick="cancelar()" >
        <input type="button" value="Confirmar" onclick="enviar()">
    </from>
    </body>
    <%@ include file="/presentation/footer.jsp" %>
</html>


<script>
    function cancelar(){
        window.location = "/Banquito/presentation/Menu/Cuenta/show";
    }
    
    function enviar(){
        var numero=document.getElementById("selectaccount").value;
        var cuenta=document.getElementById("cant").value;
        window.location ="/Banquito/presentation/Menu/Cuenta/Favorito/transfer?Naccount="+numero+"&cantidad="+cuenta;
    }
    
 </script>