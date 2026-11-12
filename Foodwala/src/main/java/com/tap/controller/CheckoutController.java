package com.tap.controller;

import java.io.IOException;

import com.tap.daoimpl.OrderDaoImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        // 1. Ensure user is logged in
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Ensure cart exists and is not empty
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("cart", cart);
        RequestDispatcher rd = req.getRequestDispatcher("/checkout.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (cart == null || cart.isEmpty() || loggedInUser == null) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Retrieve submitted parameters matching checkout.jsp input names
        String fullName = req.getParameter("fullName");
        String phoneNumber = req.getParameter("phoneNumber");
        String streetAddress = req.getParameter("streetAddress");
        String city = req.getParameter("city");
        String pincode = req.getParameter("pincode");
        String paymentMode = req.getParameter("paymentMode");

        // Validate all required fields
        if (fullName == null || fullName.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            streetAddress == null || streetAddress.trim().isEmpty() ||
            city == null || city.trim().isEmpty() ||
            pincode == null || pincode.trim().isEmpty() ||
            paymentMode == null || paymentMode.trim().isEmpty()) {

            // Pass parameters back so the JSP can retain user input values
            req.setAttribute("errorMessage", "Please fill in all required delivery and payment fields.");
            req.setAttribute("paramFullName", fullName);
            req.setAttribute("paramPhone", phoneNumber);
            req.setAttribute("paramStreet", streetAddress);
            req.setAttribute("paramCity", city);
            req.setAttribute("paramPincode", pincode);
            req.setAttribute("cart", cart);

            RequestDispatcher rd = req.getRequestDispatcher("/checkout.jsp");
            rd.forward(req, resp);
            return;
        }

        try {
            // 1. Extract restaurant ID from the first item in the cart
            CartItem firstItem = cart.getItems().iterator().next();
            int restaurantId = firstItem.getMenu().getRestaurantId();
            
            // 2. Calculate total amount (Items subtotal + ₹40 delivery fee)
            double totalAmount = cart.getTotalAmount() + 40;

            // 3. Build Order object using your exact model
            Order order = new Order();
            order.setRestaurantId(restaurantId);
            order.setUserId(loggedInUser.getUserId());
            order.setTotalAmount(totalAmount);
            order.setStatus("Placed");
            order.setPaymentMode(paymentMode);

            // 4. Save order in DB (this will populate order.getOrderId() automatically)
            OrderDaoImpl orderDao = new OrderDaoImpl();
            orderDao.addOrder(order);

            // 5. If orderId was successfully generated, save the order items
            if (order.getOrderId() > 0) {
                orderDao.addOrderItems(order.getOrderId(), cart);

                // Clear cart after successful order placement
                session.removeAttribute("cart");

                // Redirect to order history page with success flag
              //  resp.sendRedirect(req.getContextPath() + "/orders?orderSuccess=true");
                
                resp.sendRedirect(req.getContextPath() + "/order-success.jsp?orderId=" + order.getOrderId());
            } else {
                req.setAttribute("errorMessage", "Failed to place order. Please try again.");
                req.setAttribute("cart", cart);
                req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "An error occurred while processing your order.");
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
        }
    }
}