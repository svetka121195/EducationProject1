<%@ page import="app.model.User" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Светлана
  Date: 15.07.2018
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<H3>Edit user: <c:out value="${user.login}"/></H3>
<form method="post">
    <label>Name:
        <input type="text" name="name" placeholder="${user.name}"><br />
    </label>
    <label>Login:
        <input type="text" name="login" placeholder="${user.login}"><br />
    </label>
    <label>Password:
        <input type="password" name="password" placeholder="${user.password}"><br />
    </label>
    <button type="submit" name="id" value="<c:out value="${user.id}"/>">Submit</button>
</form>
</body>
</html>