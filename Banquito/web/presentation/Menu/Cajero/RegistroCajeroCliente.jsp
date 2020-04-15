
<%@page import="Banco.Presentation.Menu.Cajero.RegistroCajero.RegistroCajeroModel"%>
<%@page import="java.util.Map"%>
<%@page import="Banco.Logic.Client"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <link href="/Banquito/css/registrouser.css" rel="stylesheet" type="text/css" />
         <% session.getAttribute("cashier");%>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
       
       
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
        <% RegistroCajeroModel model= (RegistroCajeroModel) request.getAttribute("model"); %>
        <% String id = model.getCliente().getUser().getId(); %>
        <% String username = model.getCliente().getName(); %>
        
        
      <div class="box">
          <form  name="form" action = "/Banquito/presentation/Menu/Cajero/RegistroCajeroCliente/registrar" method="post">

              <h1>Datos del usuario</hl>    
                    
                  <h3>ID</h3>  
                  <input type = "text" name = "userid" placeholder="id" value=<%= id %> class = "<%=erroneo("userid", errores)%>" title="<%=title("userid", errores)%>"/>
                   <h3>Nombre</h3>  
                  
                  <input type = "text" name = "username" placeholder="Nombre" value=<%= username %> class = "<%=erroneo("username", errores)%>" title="<%=title("username", errores)%>" />
                 
                  
                 
                  
             
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