<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Warehouse Staff Home</title>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/manage-product.css" />
</head>
<body>
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/ManageProductServlet">QLST - Warehouse System</a>
    <div class="nav-right">
        <c:if test="${not empty sessionScope.admin}">
                <span class="welcome" style="margin-right:15px; font-weight:500; color:#fff;">
                    👋 Welcome, ${sessionScope.admin.fullName}!
                </span>
        </c:if>
        <a class="btn" href="${pageContext.request.contextPath}/login?action=logout">Logout</a>
    </div>
</div>

<div class="container">
    <h1>Manage Products</h1>
    <p class="subtitle">Select an action below to manage warehouse inventory</p>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/products?action=create" class="btn-primary">Add Product</a>

        <form action="UpdateProductServlet" method="get" class="form-action">
            <button type="submit" class="btn-primary">Update Product</button>
        </form>

        <a href="${pageContext.request.contextPath}/products?action=delete" class="btn-primary">Delete Product</a>
    </div>
</div>
</body>
</html>
