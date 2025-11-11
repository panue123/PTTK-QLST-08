package servlet;

import dao.ProductDAO;
import model.OrderDetail;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/OrderOnlineServlet")
public class OrderOnlineServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        HttpSession session = req.getSession();
        List<OrderDetail> cart = (List<OrderDetail>) session.getAttribute("tempOrderDetails");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("tempOrderDetails", cart);
        }

        try {
            switch (action) {
                case "search": {
                    String name = req.getParameter("name");
                    List<Product> results;

                    if (name == null || name.trim().isEmpty()) {
                        results = productDAO.getAllProduct();
                        req.setAttribute("msg", "Please enter a product name to search.");
                    } else {
                        results = productDAO.getProductByName(name);
                        if (results.isEmpty()) {
                            req.setAttribute("msg", "No product found for: " + name);
                        }
                    }

                    req.setAttribute("products", results);
                    req.getRequestDispatcher("/WEB-INF/jsp/customer/OrderOnline.jsp").forward(req, resp);
                    break;
                }

                case "add": {
                    try {
                        String message = addToCart(req, cart);
                        req.setAttribute("msg", message);
                        String requestedWith = req.getHeader("X-Requested-With");

                        if ("XMLHttpRequest".equals(requestedWith)) {
                            resp.setContentType("application/json");
                            resp.setCharacterEncoding("UTF-8");
                            resp.getWriter().write("{\"msg\": \"" + message + "\"}");
                        } else {
                            List<Product> products = productDAO.getAllProduct();
                            req.setAttribute("products", products);
                            String page = req.getParameter("page");
                            req.setAttribute("currentPage", page != null ? page : "1");
                            req.getRequestDispatcher("/WEB-INF/jsp/customer/OrderOnline.jsp").forward(req, resp);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"msg\": \"Error adding to cart!\"}");
                    }
                    break;
                }

                case "list":
                default: {
                    req.setAttribute("products", productDAO.getAllProduct());
                    req.getRequestDispatcher("/WEB-INF/jsp/customer/OrderOnline.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private String addToCart(HttpServletRequest req, List<OrderDetail> cart) throws Exception {
        int productId = Integer.parseInt(req.getParameter("productId"));
        int qty = 1;
        String qtyStr = req.getParameter("qty");
        if (qtyStr != null) {
            qty = Math.max(1, Integer.parseInt(qtyStr));
        }

        Product product = productDAO.getProductDetail(productId);

        for (OrderDetail od : cart) {
            if (od.getProduct().getId() == productId) {
                int newQty = od.getQuantity() + qty;
                if (newQty > product.getQuantity()) {
                    return "❌ Quantity exceeds stock! Available: " + product.getQuantity();
                }
                od.setQuantity(newQty);
                return "Updated quantity in cart!";
            }
        }

        if (qty > product.getQuantity()) {
            return "❌ Quantity exceeds stock! Available: " + product.getQuantity();
        }

        OrderDetail od = new OrderDetail(product, qty);
        od.setPrice(product.getPrice());
        cart.add(od);
        return "Added to cart successfully!";
    }
}
