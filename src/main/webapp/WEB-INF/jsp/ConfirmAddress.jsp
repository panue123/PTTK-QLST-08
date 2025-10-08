<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Order Confirmation</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/confirm-address.css" />
</head>
<body>

<div class="navbar">
  <a class="brand" href="${pageContext.request.contextPath}/OrderOnlineServlet">QLST</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/CheckShoppingCartServlet" class="btn-outline">
      ⬅ Back to your Shopping Cart
    </a>
  </div>
</div>

<div class="container">
  <div class="card checkout-card">
    <h2>Order Confirmation</h2>

    <c:if test="${not empty cart}">

      <!-- Customer Information -->
      <div class="checkout-section customer-info">
        <h3>Customer Information</h3>
        <div class="info-grid">
          <div class="info-item">
            <p><b>Full Name:</b> ${tmp.customer.fullName}</p>
            <p><b>Username:</b> ${tmp.customer.username}</p>
          </div>
          <div class="info-item">
            <p><b>Phone:</b> ${tmp.customer.phone}</p>
            <p><b>Email:</b> ${tmp.customer.email}</p>
          </div>
        </div>
      </div>

      <!-- Order Details -->
      <div class="checkout-section order-details">
        <h3>Your Order</h3>
        <table>
          <thead>
          <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Unit Price</th>
            <th>Subtotal</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="item" items="${cart}">
            <tr>
              <td>${item.product.name}</td>
              <td>${item.quantity}</td>
              <td>${item.product.price} VND</td>
              <td>${item.product.price * item.quantity} VND</td>
            </tr>
          </c:forEach>
          <tr>
            <td colspan="3" class="total-label">Total:</td>
            <td class="total-value">${tempOrder.totalPrice} VND</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- Delivery Address -->
      <div class="checkout-section delivery-address">
        <h3>Delivery Address</h3>
        <form method="post" action="${pageContext.request.contextPath}/ConfirmAddressServlet">
          <div class="field">
            <label for="address"><b>Select delivery address:</b></label>
            <select name="addressId" id="address" required>
              <c:forEach var="addr" items="${addresses}">
                <option value="${addr.id}">
                    ${addr.street}, ${addr.province}, ${addr.country}
                </option>
              </c:forEach>
            </select>
          </div>
          <div class="actions">
            <button class="btn" type="submit">Confirm Order</button>
          </div>
        </form>
      </div>

    </c:if>

    <c:if test="${empty cart}">
      <p>Your cart is empty.</p>
    </c:if>
  </div>
</div>

</body>
</html>
