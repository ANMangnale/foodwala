<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.tap.model.Order" %>
<%@ page import="com.tap.model.Restaurant" %>
<%@ page import="com.tap.model.OrderItem" %>
<%@ page import="com.tap.model.Menu" %>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    Map<Integer, Restaurant> restaurantMap = (Map<Integer, Restaurant>) request.getAttribute("restaurantMap");
    Map<Integer, List<OrderItem>> orderItemsMap = (Map<Integer, List<OrderItem>>) request.getAttribute("orderItemsMap");
    Map<Integer, Menu> menuMap = (Map<Integer, Menu>) request.getAttribute("menuMap");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders - FoodWala</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/orders.css">
    <style>
        .order-card {
            background-color: var(--bg-main, #e4e9f0);
            border-radius: var(--radius-md, 16px);
            box-shadow: var(--neu-inset, inset 4px 4px 8px #cbced1, inset -4px -4px 8px #ffffff);
            padding: 20px;
            margin-bottom: 25px;
        }
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid rgba(203, 206, 209, 0.6);
            padding-bottom: 12px;
            margin-bottom: 15px;
        }
        .restaurant-title {
            font-size: 18px;
            font-weight: 800;
            color: var(--text-main, #2d3748);
        }
        .order-meta {
            font-size: 13px;
            color: var(--text-muted, #718096);
            font-weight: 600;
        }
        .order-status-badge {
            background-color: #48bb78;
            color: white;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            text-transform: uppercase;
        }
        .order-item-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 8px 0;
            font-size: 15px;
            color: var(--text-main, #2d3748);
        }
        .order-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid rgba(203, 206, 209, 0.6);
            padding-top: 12px;
            margin-top: 15px;
            font-weight: 800;
        }
    </style>
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <div class="cart-page-container">
        <div class="cart-main-card">
            
            <h1 class="cart-title" style="margin-bottom: 25px;">My Order History</h1>

            <%
                if (orders == null || orders.isEmpty()) {
            %>
                <!-- Empty State -->
                <div class="cart-empty-state">
                    <div class="cart-empty-icon-box">
                        <span class="empty-cart-emoji">📦</span>
                    </div>
                    <h2 class="empty-cart-heading">No past orders found</h2>
                    <p class="empty-cart-message">
                        You haven't placed any orders yet. Explore our top restaurants and order delicious food!
                    </p>
                    <a href="<%= request.getContextPath() %>/restaurant" class="btn-explore-restaurants">
                        Explore Restaurants &rarr;
                    </a>
                </div>
            <%
                } else {
            %>
                <div class="orders-list-container">
                    <%
                        for (Order ord : orders) {
                            Restaurant rest = restaurantMap != null ? restaurantMap.get(ord.getRestaurantId()) : null;
                            List<OrderItem> items = orderItemsMap != null ? orderItemsMap.get(ord.getOrderId()) : null;
                    %>
                        <div class="order-card">
                            <!-- Header: Restaurant Name & Status -->
                            <div class="order-header">
                                <div>
                                    <div class="restaurant-title">
                                        <%= rest != null ? rest.getName() : "Restaurant #" + ord.getRestaurantId() %>
                                    </div>
                                    <div class="order-meta">
                                        Order ID: #<%= ord.getOrderId() %> &bull; <%= ord.getOrderDate() %>
                                    </div>
                                </div>
                                <div>
                                    <span class="order-status-badge"><%= ord.getStatus() %></span>
                                </div>
                            </div>

                            <!-- Order Items List -->
                            <div class="order-items-container">
                                <%
                                    if (items != null) {
                                        for (OrderItem item : items) {
                                            Menu menu = menuMap != null ? menuMap.get(item.getMenuId()) : null;
                                %>
                                    <div class="order-item-row">
                                        <div>
                                            <span style="font-weight: 700;"><%= menu != null ? menu.getItemName() : "Item #" + item.getMenuId() %></span>
                                            <span class="order-meta">&times; <%= item.getQuantity() %></span>
                                        </div>
                                        <div style="font-weight: 700; color: var(--primary, #ff5200);">
                                            ₹<%= item.getItemTotal() %>
                                        </div>
                                    </div>
                                <%
                                        }
                                    }
                                %>
                            </div>

                            <!-- Footer: Payment Mode & Total Amount -->
                            <div class="order-footer">
                                <div class="order-meta">
                                    Payment: <span style="color: var(--text-main, #2d3748);"><%= ord.getPaymentMode() %></span>
                                </div>
                                <div style="font-size: 18px; color: var(--text-main, #2d3748);">
                                    Total: <span style="color: var(--primary, #ff5200);">₹<%= ord.getTotalAmount() %></span>
                                </div>
                            </div>
                        </div>
                    <%
                        }
                    %>
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