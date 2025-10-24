<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer.css">
</head>
<body>
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/CustomerServlet">QLST</a>
    <div class="nav-right">
        <c:if test="${not empty sessionScope.customer}">
            <span class="welcome">Welcome, ${sessionScope.customer.fullName}!</span>
        </c:if>
        <a class="btn btn-outline" href="${pageContext.request.contextPath}/login?action=logout">Logout</a>
    </div>
</div>

<div class="container">
    <c:if test="${not empty msg}">
        <p style="color:#fffacd;">${msg}</p>
    </c:if>
    <div class="intro-text">
        <h2>Welcome to QLST!</h2>
        <p>Start your online shopping with just one click.</p>
    </div>

    <form action="${pageContext.request.contextPath}/OrderOnlineServlet?action=list" method="get" style="display: inline;">
        <button type="submit" class="btn">Order Online</button>
    </form>
</div>

</body>
</html>
