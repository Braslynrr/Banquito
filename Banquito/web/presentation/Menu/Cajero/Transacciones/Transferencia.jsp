<%@page import="Banco.Presentation.Menu.Cajero.Transacciones.Transaccion.TransaccionModel"%>
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
       
        <% TransaccionModel model= (TransaccionModel) session.getAttribute("model"); %>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>

        <div class="box">
          <form  name="form" action = "/Banquito/presentation/Menu/Cajero/Transacciones/Transferencia/transferencia" method="post">

               <h1>Detalles de la cuenta </hl>
                
               <h1>Propietario: <%= model.getCliente().getName() %> </hl>
               
                <h1>Numero de cuenta:  <%= model.getCuenta().getNumber() %> </hl>
                 <h1>Slado actual:  <%= model.getCuenta().getBalance() %> </hl>
                 <h1>Digite el numero de la cuenta destino: </hl><br><br>
                 
                 
                 <input type = "text" name = "numcuenta" placeholder="Cuenta destino" class = "<%=erroneo("numcuenta", errores)%>" title="<%=title("numcuenta", errores)%>" />
                 <input type = "text" name = "detail" placeholder="Motivo" class = "<%=erroneo("detail", errores)%>" title="<%=title("detail", errores)%>" />
                 <input type = "text" name = "monto" placeholder="Monto a transferir" class = "<%=erroneo("monto", errores)%>" title="<%=title("monto", errores)%>" />
                 <input type = "submit" value = "Realizar transferencia">

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