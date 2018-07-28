<%@ page import="app.model.User" %>
<%@ page import="app.model.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


<form method="post">
    <table>
        <tr>
            <th>Edit user: <c:out value="${user.login}"/></th>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" placeholder="${user.name}"></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" placeholder="${user.login}">
            <td/>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" placeholder="${user.password}"></td>
        </tr>
        <tr>
            <td>Role:</td>
            <td><input type="radio" name="role" value="user" checked>user</input><br>
                <input type="radio" name="role" value="admin">admin</input>
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="id" value="<c:out value="${user.id}"/>">Submit</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>