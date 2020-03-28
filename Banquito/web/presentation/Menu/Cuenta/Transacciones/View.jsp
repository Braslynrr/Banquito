<%@page import="Banco.Logic.Account"%>
<%@page import="Banco.Logic.Transaction"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/box.css" rel="stylesheet" type="text/css" />
        <% List<Transaction> lista=(List<Transaction>) session.getAttribute("transactions");%>
        <% Account account= (Account) session.getAttribute("account"); %>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        <center><h1>Transaciones de Cuenta #<%= account.getNumber() %>  Cliente: <%= account.getClient().getName()%> Saldo: <%= account.getBalance() %></h1></center>
        <form class="box">
            <% for(Transaction t:lista){ %>
            <div class="tbox"> Numero de transaccion: <%= t.getNumber()%> <br> tipo:<%= t.getType() %> <br> cantidad: <%= t.getAmount() %> <%= account.getCurrency().getDescription()%><br> Fecha: <%= t.getDate().toString()%> <br> Propietario:<%=t.getAccount().getClient().getName() %> </div>
            <% } %>
            <% if(lista.isEmpty()){ %>
            <h1> No se han registrado transacciones en su cuenta!</h1>
            <% } %>
        </form>
    </body>
        <%@ include file="/presentation/footer.jsp" %>
</html>