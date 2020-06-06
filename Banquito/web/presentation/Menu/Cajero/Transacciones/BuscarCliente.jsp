<%@page import="Banco.Presentation.Menu.Cajero.Transacciones.Transaccion.TransaccionModel"%>
<%@page import="Banco.Logic.Account"%>
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
         <link href="/Banquito/css/transactions.css" rel="stylesheet" type="text/css" />
        
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
       
        <% List<Account> lista=(List<Account>) session.getAttribute("accounts");%>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
        
        <% String id = (String)session.getAttribute("id");%>
        <% String action = (String)session.getAttribute("action");%>
        <div class="box">
              <div>
                  <form name ="form2" action = "/Banquito/presentation/Menu/Cajero/Transacciones/BuscarCliente/ObtenerCuenta" method="post">
                      <h1>Buscar por Id del cliente </hl> 
                          <% if (id == null) { %>
                          <% id = "";%>
                          <% }%>

                          <input type = "text" name = "clientid" placeholder="Id del cliente"  value="<%= id%>" class = "<%=erroneo("clientid", errores)%>" title="<%=title("clientid", errores)%>" /> 
                
                      <input type = "submit" value = "Buscar">
                  </form>
                </div>  
              

          <form  name="form1" action = "<%="/Banquito/presentation/Menu/Cajero/Transacciones/BuscarCliente/"+action%>" method="post">

             <select id = "clientaccounts" name = "accounts">

                    <% if (lista == null) { %>
                    <option> Numero de cuenta </option>
                    <% }%>
                    <% if (lista != null) { %>
                    <% for (Account a : lista) {%>
                    <option><%= a.getNumber()%></option>
                    <% }%>

                    <% }%>





                </select>
               
          
                      <h1>Digitar directamente el numero de cuenta </hl> 
                      
                      <input type = "text" name = "accountnumber" placeholder="Numero de cuenta" class = "<%=erroneo("accountnumber", errores)%>" title="<%=title("accountnumber", errores)%>" /> 
            
      
                
                
                
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