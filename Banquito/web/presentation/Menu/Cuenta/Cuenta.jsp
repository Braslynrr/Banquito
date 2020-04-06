<%@page import="Banco.Logic.Client"%>
<%@page import="java.util.List"%>
<%@page import="Banco.Logic.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <link href="/Banquito/css/tabla.css" rel="stylesheet" type="text/css" />
        <% List<Account> lista=( List<Account> ) session.getAttribute("cuentas");%>
        <% Client cliente= (Client) session.getAttribute("client"); %>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        <div class="busqueda">
            <h1>Busqueda de transacciones</h1><a href="/Banquito/presentation/Menu/Cuenta/Transacciones/show"></a>
            Fecha Inicial<input id="F1" name="F1" type="date"> Fecha Final:<input id="F2" name="F2" type="date"> Dinero Minimo: <input id="dinero" name="dinero" type="text" value='0'> <input type="submit" value="Buscar" onclick="buscar()">
        </div>
        <div class="tabla">
            
           <h1>Listado de Cuentas del Cliente <%=cliente.getName()%></h1>     
           <table>
            <thead>
                <tr> <td>Numero</td> <td>Saldo</td> <td> Moneda </td></tr>
            </thead>
            <tbody>
                <% if(lista!=null){%>
                        <% for(Account a:lista){%>
                        <tr> <td><a href="/Banquito/presentation/Menu/Cuenta/Transacciones/show?numeroFld=<%=a.getNumber() %>"><%=a.getNumber() %> </td>  
                            <td><%=a.getBalance() %></td><td><%= a.getCurrency().getDescription() %></td></tr> 
<!--                        <tr> <td><form action="/Guia/presentation/cliente/cuenta/show">
                                    <input type="hidden" name="numeroFld" value="<%=a.getNumber()%>"> 
                                    <button class="link-button"> <%=a.getNumber() %> </button> </form> </td>  
                            <td><%=a.getBalance() %></td></tr>  -->              
                        <%}%>
                    <%}%>
            </tbody>
        </table>    
        </div> 
     
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>
<script>
    
    function buscar(){
        window.location = "/Banquito/presentation/Menu/Cuenta/Transacciones/show?FechaI="+document.getElementById("F1").value+"&FechaF="+document.getElementById("F2").value+"&dmin="+document.getElementById("dinero").value;
    }
</script>