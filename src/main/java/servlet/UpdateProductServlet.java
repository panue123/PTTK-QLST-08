package servlet;

import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

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
                    req.getRequestDispatcher("/WEB-INF/jsp/warehousestaff/UpdateProduct.jsp").forward(req, resp);
                    break;
                }
                case "edit": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Product p = productDAO.getProductDetail(id);
                    req.setAttribute("product", p);
                    req.getRequestDispatcher("/WEB-INF/jsp/warehousestaff/UpdateDetailedProduct.jsp").forward(req, resp);
                    break;
                }
                case "list":
                default: {
                    req.setAttribute("products", productDAO.getAllProduct());
                    req.getRequestDispatcher("/WEB-INF/jsp/warehousestaff/UpdateProduct.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
