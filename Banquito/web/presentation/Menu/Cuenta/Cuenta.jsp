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
        <% List<Account> lista=(ArrayList<Account>) session.getAttribute("cuentas");%>
    </head>
    <body>
        <div style="width:50%;margin:auto;">
        <h1>Listado de Cuentas del Cliente</h1>     
    
        <table>
            <thead>
                <tr> <td>Numero</td> <td>Saldo</td>  </tr>
            </thead>
            <tbody>
                        <% for(Account a:lista){%>
                        <tr> <td><a href="/Guia/presentation/cliente/cuenta/show?numeroFld= <%=a.getNumber() %>"><%=a.getCurrency().getDescription()%> </td>  
                        <td><%=a.getBalance() %></td></tr> 
<!--                         <tr> <td><form action="/Guia/presentation/cliente/cuenta/show">
                                    <input type="hidden" name="numeroFld" value="<%=a.getNumber()%>"> 
                                    <button class="link-button"> <%=a.getNumber() %> </button> </form> </td>  
                            <td><%=a.getBalance() %></td></tr>  -->              
                        <%}%>
            </tbody>
        </table>          
    </div> 
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>
