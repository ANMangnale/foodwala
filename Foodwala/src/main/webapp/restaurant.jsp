<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tap.model.Restaurant" %>

<%
    List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurants - FoodWala</title>

    <!-- External Neumorphic Stylesheets -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/restaurant.css">
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <!-- HERO SECTION -->
    <section class="restaurant-hero">
        <h1>Discover the Best <span>Restaurants</span></h1>
        <p>Explore delicious food from your favourite restaurants and order your next meal with FoodWala.</p>
    </section>

    <!-- RESTAURANT SECTION -->
    <section class="restaurant-section">
        <div class="section-heading-row">
            <h2>Restaurants Near You</h2>
            <% if (restaurants != null) { %>
                <span class="restaurant-count"><%= restaurants.size() %> restaurants available</span>
            <% } %>
        </div>

        <div class="restaurant-grid">
            <% if (restaurants != null && !restaurants.isEmpty()) { %>
                <% for (Restaurant restaurant : restaurants) { %>
                    <div class="restaurant-card">
                        <!-- RESTAURANT IMAGE -->
                        <div class="restaurant-image-box">
                            <% if (restaurant.getImagePath() != null && !restaurant.getImagePath().trim().isEmpty()) { %>
                                <img src="<%= restaurant.getImagePath() %>" alt="<%= restaurant.getName() %>" loading="lazy" onerror="this.style.display='none';">
                            <% } %>

                            <% if (restaurant.isActive()) { %>
                                <span class="restaurant-status status-open">Open Now</span>
                            <% } else { %>
                                <span class="restaurant-status status-closed">Closed Now</span>
                            <% } %>
                        </div>

                        <!-- RESTAURANT DETAILS -->
                        <div class="restaurant-details">
                            <div class="restaurant-name-row">
                                <h3 class="restaurant-title"><%= restaurant.getName() %></h3>
                                <span class="restaurant-rating">★ <%= restaurant.getRating() %></span>
                            </div>

                            <p class="restaurant-cuisine"><%= restaurant.getCuisineType() %></p>

                            <div class="restaurant-meta-info">
                                <div class="meta-item">
                                    <span>🕒</span> <%= restaurant.getDeliveryTime() %> mins delivery
                                </div>
                                <div class="meta-item">
                                    <span>📍</span> <%= restaurant.getAddress() %>
                                </div>
                            </div>

                            <!-- VIEW MENU LINK -->
                            <% if (restaurant.isActive()) { %>
                                <a href="<%= request.getContextPath() %>/menu?restaurantId=<%= restaurant.getRestaurantId() %>" class="view-menu-link">
                                    View Menu
                                </a>
                            <% } else { %>
                                <a href="#" class="view-menu-link disabled" aria-disabled="true">Currently Closed</a>
                            <% } %>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="restaurant-empty">
                    <h3>No Restaurants Available</h3>
                    <p>We couldn't find any restaurants right now. Please check again later.</p>
                </div>
            <% } %>
        </div>
    </section>

    <!-- Modular Footer Fragment -->
    <%@ include file="/includes/footer.jsp" %>

</body>
</html>