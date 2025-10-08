package servlet;

import dao.UserDAO;
import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            req.setAttribute("msg", "logout_success");
        }
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Please enter your username and password");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        try {
            Integer userId = userDAO.login(username, password);

            if (userId != null && userId > 0) {
                HttpSession session = req.getSession();
                Customer customer = customerDAO.findByUserId(userId);
                session.setAttribute("customer", customer);
                session.setAttribute("role", customer.getRole());

                if (customer.getRole().equals("CUSTOMER")) {
                    req.getRequestDispatcher("/WEB-INF/jsp/Customer.jsp").forward(req, resp);
                } else {
                    session.setAttribute("admin", customer);
                    req.getRequestDispatcher("/WEB-INF/jsp/WarehouseStaff.jsp").forward(req, resp);
                }

            } else {
                req.setAttribute("error", "Wrong username or password. Please check again");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
