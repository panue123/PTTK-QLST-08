<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product - Warehouse System</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-detailed-product.css">
</head>
<body>

<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/UpdateDetailedProductServlet">Warehouse System</a>
    <div class="nav-right">
        <c:if test="${not empty sessionScope.admin}">
            <span class="welcome">👋 Welcome, ${sessionScope.admin.fullName}!</span>
        </c:if>
        <a class="btn logout" href="${pageContext.request.contextPath}/login?action=logout">Logout</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <h2>Edit Product Detail</h2>

        <c:if test="${not empty product}">
            <form method="post" action="${pageContext.request.contextPath}/UpdateDetailedProductServlet">
                <input type="hidden" name="action" value="save" />
                <input type="hidden" name="id" value="${product.id}" />

                <label for="name">Product Name:</label>
                <input type="text" id="name" name="name" value="${product.name}" required />

                <label for="description">Description:</label>
                <textarea id="description" name="description" rows="4">${product.description}</textarea>

                <label for="price">Price:</label>
                <input type="number" id="price" step="0.01" name="price" value="<fmt:formatNumber value='${product.price}' type='number' pattern='##########.##'/>" required />


                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" value="${product.quantity}" required />

                <button type="submit" class="btn">Save Changes</button>
            </form>
        </c:if>

        <c:if test="${empty product}">
            <p class="alert">❌ Product not found.</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/UpdateProductServlet" class="btn btn-outline">
            ← Back to Product List
        </a>
    </div>
</div>

</body>
</html>
