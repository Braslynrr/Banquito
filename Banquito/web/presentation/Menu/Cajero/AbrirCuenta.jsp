
<%@page import="Banco.Presentation.Menu.Cajero.AbrirCuenta.AbrirCuentaModel"%>
<%@page import="Banco.Logic.Currency"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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
         <link href="/Banquito/css/registrouser.css" rel="stylesheet" type="text/css" />
        
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
       
        <% AbrirCuentaModel model= (AbrirCuentaModel) request.getAttribute("model"); %>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
     
        <div class="box">
          <form  name="form" action = "/Banquito/presentation/Menu/Cajero/Registro/registrar" method="post">

                

         
                  <input type = "submit" value = "Siguiente">


                  </form>
                  </div>
     

                  </script>   

                  </body>
     <%@ include file="/presentation/footer.jsp" %>
</html>


<%!
    private String erroneo(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return "is-invalid";
      else
        return "";
    }
    private String title(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return errores.get(campo);
      else
        return "";
    }

  
    
%> 