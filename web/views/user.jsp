<%@ page import="app.model.User" %>
<%@ page import="app.model.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Светлана
  Date: 29.07.2018
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>

<head>
    <title>User</title>
</head>
<body>

<p>Hello, <c:out value="${sessionScope.currentUser.name}"/> </p>



<table>
    <tr>
        <th>Your profile:</th>
    </tr>
    <tr>
        <td>Name:</td>
        <td><c:out value="${sessionScope.currentUser.name}"/></td>
    </tr>
    <tr>
        <td>Login:</td>
        <td><c:out value="${sessionScope.currentUser.login}"/><td/>
    </tr>
    <tr>
        <td>Password:</td>
        <td><c:out value="${sessionScope.currentUser.password}"/></td>
    </tr>
    <tr>
        <td>Role:</td>
        <td><c:out value="${sessionScope.currentUser.role}"/></td>
    </tr>
</table>

</body>
</html>

