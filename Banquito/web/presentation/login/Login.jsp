<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Banco.Presentation.Login.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banquito</title>
        <%@ include file="/presentation/Head.jsp" %>
         <% Model model= (Model) request.getAttribute("model"); %>
        <% Map<String,String> errores = (Map<String,String>) request.getAttribute("errores"); %>
        <% Map<String,String[]> form = (errores==null)?this.getForm(model):request.getParameterMap();%>
        <link href="/Banquito/css/logincss.css" rel="stylesheet" type="text/css" />
       
    </head>
    <body>
        
        
        
        <%@ include file="/presentation/header.jsp" %>
       
        
        <<form class="box" method="post">
            <h1>Log in</h1>
            <div id="usuario" class="campo"><input type="text" placeholder="usuario"> </div>
            <div id="pass" class="campo"><input type="password" placeholder="contraseña"></div>
            <input type="submit">
        </form>
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

    private Map<String,String[]> getForm(Model model){
       Map<String,String[]> values = new HashMap<>();
       values.put("cedulaFld", new String[]{model.getCurrent().getId()});
       values.put("claveFld", new String[]{model.getCurrent().getPassword()});
       return values;
    }
    
%> 