<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
        <link href="/Banquito/css/logincss.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
        
        
        <<form class="box" method="post">
            <h1>Log in</h1>
            <div id="usuario" class="campo"><input type="text" placeholder="usuario"></div>
            <div id="pass" class="campo"><input type="password" placeholder="contraseña"></div>
            <input type="submit">
        </form>
    </body>
    <%@ include file="/presentation/footer.jsp" %>
</html>
