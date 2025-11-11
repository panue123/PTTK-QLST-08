<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Warehouse Staff Home</title>
        <meta charset="UTF-8" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/warehouse.css" />
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
            <h1>Welcome back,
                <c:out value="${sessionScope.admin != null ? sessionScope.admin.fullName : 'Warehouse Staff'}"/>!
            </h1>

            <p class="subtitle">
                You are logged into the warehouse management panel.
                Manage products efficiently and keep the warehouse running smoothly.
            </p>

            <form action="ManageProductServlet" method="get" style="display:inline;">
                <button type="submit" class="btn-primary">Manage Products</button>
            </form>

            <div class="info">
                <p>📦 As warehouse staff, your main responsibility is to ensure stock accuracy and monitor product data.</p>
                <p>📝 Always verify quantities when updating stock and review messages from the system regularly.</p>
                <p>⚙️ Keeping the warehouse organized helps the business operate efficiently and avoid errors in order processing.</p>
            </div>
        </div>
    </body>
</html>
