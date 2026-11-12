<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
<%@ page import="java.util.List" %>
<%@ page import="com.tap.model.Menu" %>
<%@ page import="com.tap.model.Cart" %>

<%
    List<Menu> menuList = (List<Menu>) request.getAttribute("allMenu");
    Cart cart = (Cart) session.getAttribute("cart");

    if (cart == null) {
        cart = new Cart();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FoodWala - Menu</title>

    <!-- External Neumorphic Stylesheets -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/menu.css">
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <div class="menu-container">

        <a href="<%= request.getContextPath() %>/restaurant"
           class="menu-back-link">
            Back to Restaurants
        </a>

        <%
            if (menuList != null && !menuList.isEmpty()) {
        %>

            <div class="menu-grid">

                <%
                    for (Menu menu : menuList) {
                        int quantity = cart.getItemQuantity(menu.getMenuId());
                %>

                    <div class="menu-card">

                        <!-- Menu Item Image -->
                        <div class="menu-image-container">
                            <img src="<%= menu.getImagePath() %>"
                                 alt="<%= menu.getItemName() %>"
                                 class="menu-image"
                                 loading="lazy"
                                 onerror="this.style.display='none';">
                        </div>

                        <!-- Menu Content Details -->
                        <div class="menu-content">
                            <div class="menu-name">
                                <%= menu.getItemName() %>
                            </div>
                            <div class="menu-description">
                                <%= menu.getDescription() %>
                            </div>
                            <div class="menu-price">
                                ₹<%= menu.getPrice() %>
                            </div>
                        </div>

                        <!-- Menu Bottom Actions / Quantity Controls -->
                        <div class="menu-bottom">

                            <%
                                if (menu.isAvailable()) {
                                    if (quantity == 0) {
                            %>

                                        <form action="<%= request.getContextPath() %>/cart"
                                              method="post"
                                              style="margin: 0; width: 100%;">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="menuId" value="<%= menu.getMenuId() %>">
                                            <button type="submit" class="menu-add-btn">
                                                Add to Cart
                                            </button>
                                        </form>

                            <%
                                    } else {
                            %>

                                        <div class="menu-quantity-box">
                                            <!-- MINUS BUTTON -->
                                            <form action="<%= request.getContextPath() %>/cart" method="post" style="margin: 0;">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="menuId" value="<%= menu.getMenuId() %>">
                                                <input type="hidden" name="source" value="menu">
                                                <input type="hidden" name="restaurantId" value="<%= menu.getRestaurantId() %>">
                                                <input type="hidden" name="quantity" value="<%= quantity - 1 %>">
                                                <button type="submit" class="menu-qty-btn" <%= quantity <= 0 ? "disabled" : "" %>>
                                                    −
                                                </button>
                                            </form>

                                            <!-- QUANTITY DISPLAY -->
                                            <span class="menu-qty-number">
                                                <%= quantity %>
                                            </span>

                                            <!-- PLUS BUTTON -->
                                            <form action="<%= request.getContextPath() %>/cart" method="post" style="margin: 0;">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="menuId" value="<%= menu.getMenuId() %>">
                                                <input type="hidden" name="source" value="menu">
                                                <input type="hidden" name="restaurantId" value="<%= menu.getRestaurantId() %>">
                                                <input type="hidden" name="quantity" value="<%= quantity + 1 %>">
                                                <button type="submit" class="menu-qty-btn">
                                                    +
                                                </button>
                                            </form>
                                        </div>

                            <%
                                    }
                                } else {
                            %>
                                    <button class="menu-disabled-btn" disabled>
                                        Not Available
                                    </button>
                            <%
                                }
                            %>

                        </div>

                    </div>

                <%
                    }
                %>

            </div>

        <%
            } else {
        %>
            <div class="menu-empty">
                <p>No menu items available for this restaurant right now.</p>
            </div>
        <%
            }
        %>

    </div>

    <!-- Modular Footer Fragment -->
    <%@ include file="/includes/footer.jsp" %>

</body>
</html>