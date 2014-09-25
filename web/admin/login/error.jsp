<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/admin/login/css/loginstyle.css" rel="stylesheet" type="text/css">
    </head>
    <body><div id="loginBox">
        

<table width="100%" border="0" cellpadding="8" >
  <tr>
    <td colspan="2" style="text-align: center; font-size: 16px; color: red" bgcolor="#CCCCCC">401 Ошибка авторизации</td>
  </tr>
  <tr>
    
    <td colspan="2" style="text-align: center">
        <p/><p/>
        Введен неправильный логин или пароль.<p/>
        Пожалуйста, попробуйте ещё раз.<p/>
        <a href="${pageContext.request.contextPath}/faces/admin/index.xhtml">Вернуться к авторизации</a></td>
  </tr>
</table>      
        
        
    </div></body>
</html>