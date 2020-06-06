<%@page import="Banco.Presentation.Menu.Cuenta.Favoritos.FavoritosModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eliminar favorito</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/transferF.css" rel="stylesheet" type="text/css" />
        <% FavoritosModel model =(FavoritosModel) session.getAttribute("model"); %> 
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
         
        <from class="box">
        <h3>Eliminar Cuenta</h3>
            <% if(model.getAccount()!=null){ %>
            <div class ="tabla">
            <table>
            <thead>
                <tr> <td>Numero</td> <td>Propietario</td> <td> Moneda </td></tr>
            </thead>
            <tbody>
                <tr> <td><%= model.getAccount().getNumber() %> </td>  
                     <td><%= model.getAccount().getClient().getName() %></td>
                     <td><%= model.getAccount().getCurrency().getDescription() %></td>
                </tr>          
            </tbody>
            </table> 
            </div>
             <input type="button" value="Cancelar" onclick="cancelar()" >
             <input type="button" value="Confirmar" onclick="enviar()">
            <% } %>
            <% if(model.getAccount()==null){ %>
                <h1> La cuenta no Existe! </h1>
                <input type="button" value="Volver" onclick="cancelar()" >
            <% } %>
        </from>
         
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>
<script>
    function cancelar(){
         window.location = "/Banquito/presentation/Menu/Cuenta/show";
    }
    function enviar(){
         window.location = "/Banquito/presentation/Menu/Cuenta/Favorito/Eok";
    }
</script>
