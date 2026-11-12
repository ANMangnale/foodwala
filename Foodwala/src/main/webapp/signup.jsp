<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up - FoodWala</title>

    <!-- External Neumorphic Stylesheets -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/signup.css">
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <div class="signup-page-wrapper">

        <div class="signup-card">

            <div class="signup-header-text">
                <h1>Join <span>FoodWala</span></h1>
                <p>Create an account to discover restaurants and order food</p>
            </div>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <div class="signup-error-msg">
                    <%= errorMessage %>
                </div>
            <%
                }
            %>

            <form action="<%= request.getContextPath() %>/signup" method="post">

                <div class="signup-form-group">
                    <label for="username">Full Name</label>
                    <input type="text"
                           id="username"
                           name="username"
                           class="signup-input"
                           placeholder="Enter your full name"
                           required>
                </div>

                <div class="signup-form-group">
                    <label for="email">Email Address</label>
                    <input type="email"
                           id="email"
                           name="email"
                           class="signup-input"
                           placeholder="name@example.com"
                           required>
                </div>

                <div class="signup-form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel"
                           id="phone"
                           name="phone"
                           class="signup-input"
                           placeholder="Enter your mobile number"
                           required>
                </div>

                <div class="signup-form-group">
                    <label for="password">Password</label>
                    <input type="password"
                           id="password"
                           name="password"
                           class="signup-input"
                           placeholder="Create a strong password"
                           required>
                </div>

                <div class="signup-form-group">
                    <label for="address">Delivery Address</label>
                    <textarea id="address"
                              name="address"
                              class="signup-input"
                              placeholder="Enter your full delivery address"
                              rows="3"
                              required></textarea>
                </div>

                <button type="submit" class="signup-submit-btn">
                    Create Account 
                </button>

            </form>

            <div class="signup-card-footer">
                Already have an account? <a href="<%= request.getContextPath() %>/login">Login here</a>
            </div>

        </div>

    </div>

    <!-- Modular Footer Fragment -->
    <%@ include file="/includes/footer.jsp" %>

</body>
</html>