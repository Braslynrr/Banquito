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
        <div class="box">
            <h1>Cliente: </h1>
            <div id="cuen">
                <input type="submit" value="ir a Cuenta">
            </div>
        </div>
        <div class="box3">
            <img src="/Banquito/imagenes/wilaT.png">
        </div>
        <div class="box2">
            <h1>Cajero: </h1>
            <div id="caj">   
            <input type="submit" value="Ir al Cajero">
            </div>
        </div>
    </body>
    <%@ include file="/presentation/footer.jsp" %>
</html>
