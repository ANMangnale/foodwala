package com.tap.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false); // Fetch existing session without creating a new one
        
        if (session != null) {
            session.invalidate(); // Destroys the session, clearing 'loggedInUser', 'cart', etc.
        }
        
        // Redirect back to the login page
        resp.sendRedirect(req.getContextPath() + "/restaurant");
    }
}