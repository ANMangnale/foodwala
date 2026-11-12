package com.tap.controller;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.daoimpl.UserDaoImpl;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signup")
public class SignupController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve form parameters
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String address = req.getParameter("address");

        // Comprehensive validation using trim() and isEmpty() for ALL fields
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            
            req.setAttribute("errorMessage", "All required fields must be filled out.");
            RequestDispatcher rd = req.getRequestDispatcher("/signup.jsp");
            rd.forward(req, resp);
            return;
        }

        try {
            UserDaoImpl userDAO = new UserDaoImpl();

            // NEW: Check if the email already exists in the database
            User existingUser = userDAO.getUserByEmail(email.trim());
            if (existingUser != null) {
                req.setAttribute("errorMessage", "An account with this email already exists. Please login instead.");
                RequestDispatcher rd = req.getRequestDispatcher("signup.jsp");
                rd.forward(req, resp);
                return;
            }

            // Construct User model and trim whitespace for every field
            User user = new User();
            user.setUsername(username.trim());
            user.setEmail(email.trim());
            user.setPhone(phone.trim());
            
            String en_password = BCrypt.hashpw(password, BCrypt.gensalt());;
            user.setPassword(en_password);
            user.setAddress(address.trim());
            user.setRole("Customer");

            // save user in database
            
            userDAO.addUser(user);

            // Success: Redirect to login page  or registration successful
            
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", user);
            
            resp.sendRedirect(
                    req.getContextPath() + "/restaurant");
     
//            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Registration failed due to a server error. Please try again.");
            RequestDispatcher rd = req.getRequestDispatcher("signup.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }
}