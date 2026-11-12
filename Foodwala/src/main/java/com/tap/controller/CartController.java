package com.tap.controller;

import java.io.IOException;

import com.tap.dao.MenuDao;
import com.tap.daoimpl.MenuDaoImpl;
import com.tap.model.Cart;
import com.tap.model.Menu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        int menuId = Integer.parseInt(req.getParameter("menuId"));

        if ("add".equals(action)) {

            MenuDaoImpl menuDAO = new MenuDaoImpl();

            Menu menu = menuDAO.getMenu(menuId);

            if (menu != null && menu.isAvailable()) {

                HttpSession session = req.getSession();

                Cart cart = (Cart) session.getAttribute("cart");

                if (cart == null) {

                    cart = new Cart();

                    session.setAttribute("cart", cart);
                }

                boolean added = cart.addItem(menu);

                if (added) {

                    session.setAttribute("cart", cart);

                    resp.sendRedirect(
                        req.getContextPath()
                        + "/menu?restaurantId="
                        + menu.getRestaurantId()
                    );

                } else {

                    resp.sendRedirect(
                        req.getContextPath()
                        + "/menu?restaurantId="
                        + menu.getRestaurantId()
                        + "&cartError=differentRestaurant"
                    );
                }
            }
        }

        else if ("update".equals(action)) {

            int quantity = Integer.parseInt(req.getParameter("quantity"));

            String source = req.getParameter("source");

            HttpSession session = req.getSession();

            Cart cart = (Cart) session.getAttribute("cart");

            if (cart != null) {

                cart.updateQuantity(menuId, quantity);

                session.setAttribute("cart", cart);
            }

            if ("menu".equals(source)) {

                int restaurantId =
                        Integer.parseInt(req.getParameter("restaurantId"));

                resp.sendRedirect(
                    req.getContextPath()
                    + "/menu?restaurantId="
                    + restaurantId
                );
            }

            else if ("cart".equals(source)) {

                resp.sendRedirect(
                    req.getContextPath()
                    + "/cart"
                );
            }
        }

        else if ("clear".equals(action)) {

            HttpSession session = req.getSession();

            Cart cart = (Cart) session.getAttribute("cart");

            if (cart != null) {

                cart.clear();
            }

            resp.sendRedirect(
                req.getContextPath() + "/cart"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher rd =
                req.getRequestDispatcher("cart.jsp");

        rd.forward(req, resp);
    }
}