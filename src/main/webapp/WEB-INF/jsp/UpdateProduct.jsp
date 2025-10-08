<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Management - Warehouse System</title>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-product.css" />
</head>
<body>
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/UpdateProductServlet">QLST - Warehouse System</a>
    <div class="nav-right">
        <c:if test="${not empty sessionScope.admin}">
            <span class="welcome">👋 Welcome, ${sessionScope.admin.fullName}!</span>
        </c:if>
        <a class="btn logout" href="${pageContext.request.contextPath}/login?action=logout">Logout</a>
    </div>
</div>

<div class="container">
    <h2>Search Products</h2>
    <form method="get" action="${pageContext.request.contextPath}/UpdateProductServlet">
        <input type="hidden" name="action" value="search"/>
        <input type="text" name="name" placeholder="Enter product name..." />
        <input type="submit" value="Search" class="btn-search" />
    </form>

    <c:if test="${not empty msg}">
        <div class="alert", color="red">${msg}</div>
    </c:if>

    <c:if test="${not empty products}">
        <table class="product-table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.description}</td>
                    <td>${p.price} VND</td>
                    <td>${p.quantity}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/UpdateProductServlet">
                            <input type="hidden" name="action" value="edit"/>
                            <input type="hidden" name="id" value="${p.id}"/>
                            <input type="submit" value="Edit" class="btn-edit"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty products}">
        <p class="no-data">No products found.</p>
    </c:if>
</div>
</body>
</html>
