<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User" %>

<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>

<header class="navbar-container">
    <div class="logo">
        Food<span>Wala</span>
    </div>

    <nav class="nav-links">
        <a href="<%= request.getContextPath() %>/restaurant">Restaurants</a>
        <a href="<%= request.getContextPath() %>/cart">Cart</a>
        <a href="<%= request.getContextPath() %>/orders">Orders</a>

        <% if (loggedInUser != null) { %>
            <a href="<%= request.getContextPath() %>/profile" class="profile-pill">
                <span class="profile-icon">👤</span>
                <span><%= loggedInUser.getUsername() %></span>
            </a>
            
            <a href="<%= request.getContextPath() %>/logout" class="login-nav-btn">Logout</a>
            
        <% } else { %>
            <a href="<%= request.getContextPath() %>/login" class="login-nav-btn">Login</a>
        <% } %>
    </nav>
</header>