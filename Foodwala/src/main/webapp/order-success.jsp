<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String orderId = request.getParameter("orderId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Successful - FoodWala</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css">
</head>
<body>

    <%@ include file="/includes/navbar.jsp" %>

    <div class="cart-page-container">
        <div class="cart-main-card" style="text-align: center; padding: 40px 20px;">
            <div class="cart-empty-icon-box" style="margin: 0 auto 20px auto; width: 80px; height: 80px; display: flex; align-items: center; justify-content: center; border-radius: 50%; background: #e4e9f0; box-shadow: var(--neu-outset, 6px 6px 12px #cbced1, -6px -6px 12px #ffffff);">
                <span style="font-size: 36px;">🎉</span>
            </div>
            
            <h1 class="cart-title" style="margin-bottom: 10px;">Order Placed Successfully!</h1>
            
            <p class="cart-empty-message" style="margin-bottom: 25px;">
                Thank you for your order! Your order <strong>#<%= orderId != null ? orderId : "" %></strong> has been confirmed and is being prepared.
            </p>

            <div style="display: flex; justify-content: center; gap: 15px; flex-wrap: wrap;">
                <a href="<%= request.getContextPath() %>/orders" class="btn-explore-restaurants" style="text-decoration: none;">
                    View Order History
                </a>
                <a href="<%= request.getContextPath() %>/restaurant" class="btn-explore-restaurants" style="text-decoration: none; background: transparent; color: var(--text-main, #2d3748);">
                    Explore More
                </a>
            </div>
        </div>
    </div>

    <%@ include file="/includes/footer.jsp" %>

</body>
</html>