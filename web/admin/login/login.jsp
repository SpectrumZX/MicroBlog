<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/admin/login/css/loginstyle.css" rel="stylesheet" type="text/css">
    </head>
    <body><div id="loginBox">
        
<form action="j_security_check" method="POST">
 
        <table width="100%" border="0" cellpadding="8" >
  <tr>
    <td colspan="2" style="text-align: center; font-size: 16px" bgcolor="#759EC8">Авторизация</td>
  </tr>
  <tr>
      <td><div id="tab"><strong>Ваш логин:</strong></div></td>
    <td><input placeholder="Введите логин" type="text" size="20" name="j_username"></td>
  </tr>
  <tr>
    <td><div id="tab"><strong>Пароль:</strong></div></td>
    <td><input placeholder="Введите пароль" type="password" size="20" name="j_password"></td>
  </tr>
  <tr>
    <td colspan="2" style="text-align: center"><input type="submit" value="Войти"></td>
  </tr>
  <tr>
    <td colspan="2" style="text-align: center"><a href="./../index.xhtml"><<На главную</a></td>
  </tr>
</table>
 </form>       
        
        
    </div></body>
</html>