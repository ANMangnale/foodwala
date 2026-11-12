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

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // FIX 1: Retrieve parameter matching name="email" in login.jsp
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDaoImpl userDao = new UserDaoImpl();

        User user = userDao.getUserByEmail(email);
        
        String en_password = BCrypt.hashpw(password, BCrypt.gensalt());
        
        System.out.println("password: " + password);
        System.out.println("En Password: " + en_password);
        System.out.println("DB Password: " + user.getPassword());

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {

            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", user);

            resp.sendRedirect(
                req.getContextPath() + "/restaurant"
            );

        } else {

            // FIX 2: Set "errorMessage" so login.jsp renders the error alert box
            req.setAttribute("errorMessage", "Invalid email or password");
            
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp")
           .forward(req, resp);
    }
}