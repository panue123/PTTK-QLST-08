<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login - QLST</title>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <c:if test="${not empty error}">
        <div class="alert">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="field">
            <label>Username</label>
            <input type="text" name="username" value="${param.username}" required/>
        </div>
        <div class="field">
            <label>Password</label>
            <input type="password" name="password" required/>
        </div>
        <button type="submit" class="btn">Login</button>
    </form>
</div>
</body>
</html>
