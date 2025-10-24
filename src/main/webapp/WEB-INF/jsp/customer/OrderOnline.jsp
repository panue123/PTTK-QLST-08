<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Order Online</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-online.css">
</head>
<body>
<div class="navbar">
  <a class="brand" href="${pageContext.request.contextPath}/OrderOnlineServlet">QLST</a>
  <div class="nav-right">
    <c:if test="${not empty sessionScope.customer}">
      <span class="welcome">Welcome, ${sessionScope.customer.fullName}!</span>
    </c:if>
    <a class="cart" href="${pageContext.request.contextPath}/CheckShoppingCartServlet">
      <i class="fa fa-shopping-cart"></i> Cart
    </a>
    <a class="btn btn-outline" href="${pageContext.request.contextPath}/login?action=logout">Logout</a>
  </div>
</div>

<div class="container">

  <h2>Search Products</h2>
  <form method="get" action="${pageContext.request.contextPath}/OrderOnlineServlet">
    <input type="hidden" name="action" value="search"/>
    <input type="text" name="name" placeholder="Enter product name..."/>
    <input type="submit" value="Search"/>
  </form>

  <c:if test="${not empty msg}">
    <div class="alert">${msg}</div>
  </c:if>

  <c:if test="${not empty products}">
    <table class="product-table">
      <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Quantity</th>
        <th>Action</th>
      </tr>
      <c:forEach var="p" items="${products}">
        <tr>
          <td>${p.name}</td>
          <td>${p.description}</td>
          <td><fmt:formatNumber value="${p.price}" pattern="#,##0.##"/> VND</td>
          <td>${p.quantity}</td>
          <td>
            <form method="get" action="${pageContext.request.contextPath}/OrderOnlineServlet">
              <input type="hidden" name="action" value="add"/>
              <input type="hidden" name="productId" value="${p.id}"/>
              <input type="number" name="qty" min="1" max="${p.quantity}" value="1" required/>
          </td>
          <td>
            <input type="submit" value="Add to Cart" class="btn small"/>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:if>

  <c:if test="${empty products}">
    <p>No products found.</p>
  </c:if>

</div>
</body>
</html>
