
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

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
       
        
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>

        <div class="box">
          <form  name="form" action = "/Banquito/presentation/Menu/Cajero/AbrirCuenta/registrar" method="post">

              <div
                  <form name ="subform" method="post">
                      <h1>Digite el id del cliente y seleccione la cuenta </hl> 
                     <input type = "text" name = "clientid" placeholder="Id del cliente" class = "<%=erroneo("clientid", errores)%>" title="<%=title("clientid", errores)%>" /> 
                  </form>
                </div> 
               
                
      
                
                
                
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