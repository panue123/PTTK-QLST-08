package servlet;

import dao.AddressDAO;
import dao.OnlineOrderDAO;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ConfirmAddressServlet")
public class ConfirmAddressServlet extends HttpServlet {

    private OnlineOrderDAO orderDAO = new OnlineOrderDAO();
    private AddressDAO addressDAO = new AddressDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            resp.sendRedirect(req.getContextPath() + "/login?msg=please_login");
            return;
        }
        List<OrderDetail> cart = (List<OrderDetail>) session.getAttribute("tempOrderDetails");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?msg=cart_empty");
            return;
        }
        OnlineOrder tmp = (OnlineOrder) session.getAttribute("tempOrder");
        try {
            List<Address> addresses = addressDAO.getAddressOfCustomer(customer.getId());
            req.setAttribute("addresses", addresses);
            req.setAttribute("cart", cart);
            req.setAttribute("tmp", tmp);
            req.getRequestDispatcher("/WEB-INF/jsp/ConfirmAddress.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Unable to get address list", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        List<OrderDetail> orderDetails = (List<OrderDetail>) session.getAttribute("tempOrderDetails");

        if (customer == null || orderDetails == null || orderDetails.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?msg=checkout_error");
            return;
        }

        String addressIdStr = req.getParameter("addressId");
        if (addressIdStr == null) {
            resp.sendRedirect(req.getContextPath() + "/ConfirmAddressServlet?msg=no_address_selected");
            return;
        }

        try {
            int addressId = Integer.parseInt(addressIdStr);
            List<Address> addresses = addressDAO.getAddressOfCustomer(customer.getId());
            Address selectedAddress = null;
            for (Address addr : addresses) {
                if (addr.getId() == addressId) {
                    selectedAddress = addr;
                    break;
                }
            }

            if (selectedAddress == null) {
                resp.sendRedirect(req.getContextPath() + "/ConfirmAddressServlet?msg=invalid_address");
                return;
            }

            float totalPrice = 0f;
            for (OrderDetail od : orderDetails) {
                od.setPrice(od.getProduct().getPrice());
                totalPrice += od.getPrice() * od.getQuantity();
            }
            req.setAttribute("totalPrice", totalPrice);

            OnlineOrder order = new OnlineOrder();
            order.setCustomer(customer);
            order.setAddress(selectedAddress);
            order.setTotalPrice(totalPrice);
            order.setStatus("PENDING");
            order.setOrderDate(java.time.LocalDateTime.now());

            int orderId = orderDAO.createOrder(order, orderDetails);

            if (orderId > 0) {
                session.removeAttribute("tempOrderDetails");
                req.setAttribute("msg", "Checkout successful!");
                req.getRequestDispatcher("/WEB-INF/jsp/Customer.jsp").forward(req, resp);
            } else {
                req.setAttribute("msg", "Fail to checkout!");
                req.getRequestDispatcher("/WEB-INF/jsp/Customer.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error during checkout", e);
        }
    }
}
