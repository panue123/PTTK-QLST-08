package servlet;

import model.Product;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateDetailedProductServlet")
public class UpdateDetailedProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("save".equals(action)) {
                Product p = new Product(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("description"),
                        Float.parseFloat(req.getParameter("price")),
                        Integer.parseInt(req.getParameter("quantity"))
                );
                productDAO.updateProduct(p);
                req.setAttribute("msg", "Updated sucessfully");
                req.setAttribute("products", productDAO.getAllProduct());
                req.getRequestDispatcher("/WEB-INF/jsp/UpdateProduct.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("msg", "Error: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/UpdateProduct.jsp").forward(req, resp);
        }
    }
}
