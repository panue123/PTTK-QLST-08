package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ManageProductServlet")
public class ManageProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/jsp/ManageProduct.jsp").forward(req, resp);
    }
}
