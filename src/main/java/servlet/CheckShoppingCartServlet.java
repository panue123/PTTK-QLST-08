package servlet;

import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CheckShoppingCartServlet")
public class CheckShoppingCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login&msg=please_login");
            return;
        }
        List<OrderDetail> cart = (List<OrderDetail>) session.getAttribute("tempOrderDetails");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("tempOrderDetails", cart);
        }

        String action = req.getParameter("action");
        if (action == null) action = "view";

        switch (action) {
            case "update":
                updateCart(req, cart, session);
                break;
            case "remove":
                removeFromCart(req, cart, session);
                break;
            case "checkout":
                session.setAttribute("tempOrderDetails", cart);

                OnlineOrder tempOrder = new OnlineOrder();
                tempOrder.setCustomer(customer);
                tempOrder.setTotalPrice(calculateTotal(cart));
                session.setAttribute("tempOrder", tempOrder);

                resp.sendRedirect(req.getContextPath() + "/ConfirmAddressServlet");
                return;
            default:
                break;
        }
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/jsp/customer/CheckShoppingCart.jsp").forward(req, resp);
    }

    private void updateCart(HttpServletRequest req, List<OrderDetail> cart, HttpSession session) {
        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int qty = Integer.parseInt(req.getParameter("qty"));

            for (OrderDetail od : cart) {
                if (od.getProduct().getId() == productId) {
                    int stock = od.getProduct().getQuantity();
                    if (qty > stock) {
                        session.setAttribute("cartMessage", "⚠️ Quantity exceeds stock!");
                        return;
                    }
                    od.setQuantity(qty);
                    session.setAttribute("cartMessage", "✅ Cart updated!");
                    return;
                }
            }
        } catch (NumberFormatException e) {
            session.setAttribute("cartMessage", "⚠️ Invalid input!");
        }
    }

    private void removeFromCart(HttpServletRequest req, List<OrderDetail> cart, HttpSession session) {
        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            cart.removeIf(od -> od.getProduct().getId() == productId);
            session.setAttribute("cartMessage", "🗑️ Product removed!");
        } catch (NumberFormatException e) {
            session.setAttribute("cartMessage", "⚠️ Invalid product!");
        }
    }

    private float calculateTotal(List<OrderDetail> cart) {
        float total = 0;
        for (OrderDetail od : cart) {
            total += od.getPrice() * od.getQuantity();
        }
        return total;
    }
}
