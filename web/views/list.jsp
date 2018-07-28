<%@ page import="app.model.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><%--
  Created by IntelliJ IDEA.
  User: Светлана
  Date: 13.07.2018
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
</head>
<body>
<H3>Add user:</H3>
<form method="post">
    <label>Name:
        <input type="text" name="name"><br />
    </label>
    <label>Login:
        <input type="text" name="login"><br />
    </label>
    <label>Password:
        <input type="password" name="password"><br />
    </label>
    <button type="submit">Submit</button>
</form>


<table border="1">

    <caption>Users list</caption>

    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Login</th>
        <th>Password</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>



    <c:forEach var="user" items="${usersList}">
        <tr>
            <th><c:out value="${user.id}"/></th>
            <th><c:out value="${user.name}"/></th>
            <th><c:out value="${user.login}"/></th>
            <th><c:out value="${user.password}"/></th>
            <th><form method="get" action="/edit"><button name="id" value="<c:out value="${user.id}"/>"  type="submit">Edit</button></form></th>
            <th><form method="post" action="/delete"><button name="id" value="<c:out value="${user.id}"/>"  type="submit">Delete</button></form></th>
        </tr>
    </c:forEach>

</table>


</body>
</html>
