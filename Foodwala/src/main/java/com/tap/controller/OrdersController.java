package com.tap.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.daoimpl.MenuDaoImpl;
import com.tap.daoimpl.OrderDaoImpl;
import com.tap.daoimpl.OrderItemDaoImpl;
import com.tap.daoimpl.RestaurantDaoImpl;
import com.tap.model.Menu;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.Restaurant;
import com.tap.model.User;
import com.tap.model.Cart;

@WebServlet("/orders")
public class OrdersController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // 1. Check whether the user is logged in
        if (loggedInUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 2. Create the DAO instances
        OrderDaoImpl orderDao = new OrderDaoImpl();
        RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
        MenuDaoImpl menuDao = new MenuDaoImpl();

        // 3. Get all orders of the logged-in user
        List<Order> orderList = orderDao.getOrdersByUserId(loggedInUser.getUserId());

        // 4. Create maps and lists to store related details
        Map<Integer, Restaurant> restaurantMap = new HashMap<>();
        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 5. Process every order, get restaurant, get order items, and get menu details for every order item
        if (orderList != null) {
            for (Order order : orderList) {
                // Store restaurant details
                if (!restaurantMap.containsKey(order.getRestaurantId())) {
                    Restaurant restaurant = restaurantDao.getRestaurant(order.getRestaurantId());
                    if (restaurant != null) {
                        restaurantMap.put(order.getRestaurantId(), restaurant);
                    }
                }

                // Store order items for each order
                List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderId(order.getOrderId());
                if (orderItems != null) {
                    orderItemsMap.put(order.getOrderId(), orderItems);

                    // Store menu details for every order item
                    for (OrderItem item : orderItems) {
                        if (!menuMap.containsKey(item.getMenuId())) {
                            Menu menu = menuDao.getMenu(item.getMenuId());
                            if (menu != null) {
                                menuMap.put(item.getMenuId(), menu);
                            }
                        }
                    }
                }
            }
        }

        // 6. Set attributes to pass data to the JSP view
        req.setAttribute("orders", orderList);
        req.setAttribute("restaurantMap", restaurantMap);
        req.setAttribute("orderItemsMap", orderItemsMap);
        req.setAttribute("menuMap", menuMap);

        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Cart cart = (Cart) session.getAttribute("cart");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String paymentMode = request.getParameter("paymentMode");
        if (paymentMode == null || paymentMode.trim().isEmpty()) {
            paymentMode = "Cash on Delivery";
        }

        Order order = new Order();
        order.setRestaurantId(cart.getRestaurantId());
        order.setUserId(loggedInUser.getUserId());
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus("Confirmed");
        order.setPaymentMode(paymentMode);

        OrderDaoImpl orderDao = new OrderDaoImpl();
        orderDao.addOrder(order);

        if (order.getOrderId() > 0) {
            orderDao.addOrderItems(order.getOrderId(), cart);
        }

        session.removeAttribute("cart");
     
     response.sendRedirect("order-success.jsp?orderId=" + order.getOrderId());
    }
}