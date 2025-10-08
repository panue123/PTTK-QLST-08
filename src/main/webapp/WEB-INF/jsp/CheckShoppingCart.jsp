<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shopping-cart.css">
</head>
<body>
<div class="navbar">
    <a class="brand" href="${pageContext.request.contextPath}/OrderOnlineServlet">QLST</a>
    <div class="nav-right">
        <a href="${pageContext.request.contextPath}/OrderOnlineServlet" class="btn-outline">⬅ Back to Order Online</a>
    </div>
</div>

<div class="container">
    <h2>Shopping Cart</h2>
    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="alert">${sessionScope.cartMessage}</div>
        <%
            session.removeAttribute("cartMessage");
        %>
    </c:if>

    <c:if test="${not empty cart}">
        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Action</th>
            </tr>
            <c:set var="total" value="0"/>
            <c:forEach var="od" items="${cart}">
                <c:set var="sub" value="${od.price * od.quantity}"/>
                <tr>
                    <td>${od.product.name}</td>
                    <td>${od.price} VND</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/CheckShoppingCartServlet">
                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="productId" value="${od.product.id}"/>
                            <input type="number" name="qty" min="1" max="${od.product.quantity}" value="${od.quantity}"/>
                            <input type="submit" value="Update"/>
                        </form>
                    </td>
                    <td>${sub} VND</td>
                    <td>
                        <a class="btn"
                           href="${pageContext.request.contextPath}/CheckShoppingCartServlet?action=remove&productId=${od.product.id}">
                            Remove
                        </a>
                    </td>
                </tr>
                <c:set var="total" value="${total + sub}"/>
            </c:forEach>
            <tr>
                <td colspan="3" style="text-align:right"><b>Total:</b></td>
                <td colspan="2"><b>${total} VND</b></td>
            </tr>
        </table>
        <br/>
        <form method="get" action="${pageContext.request.contextPath}/CheckShoppingCartServlet">
            <input type="hidden" name="action" value="checkout"/>
            <input type="submit" value="Proceed to Checkout" class="btn"/>
        </form>
    </c:if>

    <c:if test="${empty cart}">
        <p>Your cart is empty.</p>
    </c:if>
</div>
</body>
</html>
