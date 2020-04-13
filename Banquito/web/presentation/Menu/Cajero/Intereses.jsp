<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Banco.Presentation.Menu.Cajero.CajeroModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Añadir Favorito</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/transferF.css" rel="stylesheet" type="text/css" />
        <% CajeroModel model =(CajeroModel) session.getAttribute("model"); %> 
        <% Date hoy = (Date) session.getAttribute("hoy"); %>
    </head>
    <body>
    <%@ include file="/presentation/header.jsp" %>
         
        <from class="box">
        <h3>Añadir Cuenta</h3>
            <% if(model.getInteres()!=null){ %>
                    <% SimpleDateFormat form= new SimpleDateFormat("YYYY-MM-dd"); %>
                    <% String fecha= form.format(model.getInteres()); %>
                    <% if(hoy.after(model.getInteres()) || hoy.equals(model.getInteres())){ %>
                        <h1> Aplicar intereses </h1>
                        <input type="button" value="Aplicar" onclick="enviar()" >
                        <input type="button" value="Volver" onclick="cancelar()" >
                    <% } %>
                    <%if(hoy.before(model.getInteres())){%>
                        <h1>Intereses aplicables hasta <%= fecha%> </h1>
                        <input type="button" value="Volver" onclick="cancelar()" >
                    <%}%>
                    
            <% } %>
            <% if(model.getInteres()==null){ %>
                <h1> Aplicar intereses </h1>
                <input type="button" value="Aplicar" onclick="enviar()" >
                <input type="button" value="Volver" onclick="cancelar()" >
            <% } %>
        </from>
         
    </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>
<script>
    function cancelar(){
         window.location = "/Banquito/presentation/Menu/Cajero/show";
    }
    function enviar(){
         window.location = "/Banquito/presentation/Menu/Cajero/Aplicar";
    }
</script>