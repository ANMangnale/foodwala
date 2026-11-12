<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - FoodWala</title>

    <!-- External Neumorphic Stylesheets -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body>

    <!-- Modular Navbar Fragment -->
    <%@ include file="/includes/navbar.jsp" %>

    <div class="login-page-wrapper">

        <div class="login-card">

            <div class="login-header-text">
                <h1>Welcome to <span>FoodWala</span></h1>
                <p>Login to your account to order delicious meals</p>
            </div>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <div class="login-error-msg">
                    <%= errorMessage %>
                </div>
            <%
                }
            %>

            <form action="<%= request.getContextPath() %>/login" method="post">

                <div class="login-form-group">
                    <label for="email">Email</label>
                    <input type="text"
                           id="email"
                           name="email"
                           class="login-input"
                           placeholder="Enter your email"
                           required>
                </div>

                <div class="login-form-group">
                    <label for="password">Password</label>
                    <input type="password"
                           id="password"
                           name="password"
                           class="login-input"
                           placeholder="Enter your password"
                           required>
                </div>

                <button type="submit" class="login-submit-btn">
                    Login 
                </button>

            </form>

            <div class="login-card-footer">
                Don't have an account? <a href="<%= request.getContextPath() %>/signup">Sign Up</a>
            </div>

        </div>

    </div>

    <!-- Modular Footer Fragment -->
    <%@ include file="/includes/footer.jsp" %>

</body>
</html>