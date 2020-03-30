
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Banco.Logic.Cashier"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Banco.Presentation.Menu.Cajero.Registro.RegistroModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <link href="/Banquito/css/logincss.css" rel="stylesheet" type="text/css" />
         <% session.getAttribute("cashier");%>
    </head>
    <body>
        <%@ include file="/presentation/header.jsp" %>
       
        <% RegistroModel model= (RegistroModel) request.getAttribute("model"); %>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
        <% Map<String,String[]> form = (errores==null)?this.getForm(model):request.getParameterMap();%>
        
         <<form class="box">
            <h1>Ingrese los datos del nuevo usuario </h1>
            
            <div id="usuario" class="campo"><input class="<%=erroneo("userid",errores)%>" placeholder="usuario" type="text" name="userid" value="<%=form.get("userid")[0]%>" title="<%=title("userid",errores)%>"></div>
            <div id="pass" class="campo"><input class="<%=erroneo("userpass",errores)%>" placeholder="clave" type="password" name="userpass" value="<%=form.get("userpass")[0]%>" title="<%=title("userpass",errores)%>"></div>
    
        </form>
        
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

    private Map<String,String[]> getForm(RegistroModel model){
       Map<String,String[]> values = new HashMap<>();
      
       return values;
    }
    
%> 