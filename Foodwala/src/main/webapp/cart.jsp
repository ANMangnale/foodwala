<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.tap.model.Cart" %>
<%@ page import="com.tap.model.CartItem" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Cart - FoodWala</title>

    <!-- External Neumorphic Stylesheets with Cache-Busting -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css?v=4">
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <div class="cart-page-container">

        <div class="cart-main-card">

            <%
                if (cart == null || cart.isEmpty()) {
            %>

                <!-- Compact Empty Cart State without extra space -->
                <div class="cart-empty-state">
                    <div class="cart-empty-icon-box">
                        <span class="empty-cart-emoji">🛒</span>
                    </div>
                    <h2 class="empty-cart-heading">Your Cart is Empty</h2>
                    <p class="empty-cart-message">
                        Explore our top-rated restaurants and add delicious meals to your cart.
                    </p>

                    <!-- Neumorphic Orange Explore Button -->
                    <a href="<%= request.getContextPath() %>/restaurant" class="btn-explore-restaurants">
                        Explore Restaurants 
                    </a>
                </div>

            <%
                } else {
            %>

                <h1 class="cart-title">My Food Cart</h1>

                <div class="cart-items-list">

                    <%
                        for (CartItem item : cart.getItems()) {
                    %>

                        <div class="cart-item-row">

                            <!-- Item Image -->
                            <img src="<%= item.getMenu().getImagePath() %>"
                                 alt="<%= item.getMenu().getItemName() %>"
                                 class="cart-item-img"
                                 loading="lazy"
                                 onerror="this.style.display='none';">

                            <!-- Item Details -->
                            <div class="cart-item-details">
                                <div class="cart-item-name">
                                    <%= item.getMenu().getItemName() %>
                                </div>
                                <div class="cart-item-price">
                                    Price: ₹<%= item.getMenu().getPrice() %>
                                </div>
                            </div>

                            <!-- Quantity Controls -->
                            <div class="cart-quantity-box">
                                <!-- Minus Form -->
                                <form action="<%= request.getContextPath() %>/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="menuId" value="<%= item.getMenu().getMenuId() %>">
                                    <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                                    <input type="hidden" name="source" value="cart">
                                    <button type="submit" class="cart-qty-btn">−</button>
                                </form>

                                <span class="cart-qty-number">
                                    <%= item.getQuantity() %>
                                </span>

                                <!-- Plus Form -->
                                <form action="<%= request.getContextPath() %>/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="menuId" value="<%= item.getMenu().getMenuId() %>">
                                    <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                                    <input type="hidden" name="source" value="cart">
                                    <button type="submit" class="cart-qty-btn">+</button>
                                </form>
                            </div>

                            <!-- Item Subtotal -->
                            <div class="cart-item-subtotal">
                                ₹<%= item.getSubtotal() %>
                            </div>

                        </div>

                    <%
                        }
                    %>

                </div>

                <!-- Cart Footer & Checkout -->
                <div class="cart-footer-section">
                    <div class="cart-total-amount">
                        Total Amount: <span>₹<%= cart.getTotalAmount() %></span>
                    </div>

                    <form action="<%= request.getContextPath() %>/checkout" method="get">
                        <button type="submit" class="cart-checkout-btn">
                            Proceed to Checkout 
                        </button>
                    </form>
                </div>

            <%
                }
            %>

        </div>

    </div>

    <!-- Modular Footer Fragment -->
    <%@ include file="/includes/footer.jsp" %>

</body>
</html>