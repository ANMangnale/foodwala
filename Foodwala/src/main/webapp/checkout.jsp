<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.tap.model.Cart"%>
<%@ page import="com.tap.model.CartItem"%>
<%@ page import="com.tap.model.User"%>
<%@ page import="java.util.Collection"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - FoodWala</title>

    <!-- External Neumorphic Stylesheets -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/checkout.css">
</head>
<body>

    <!-- Modular Navbar Fragment (Already declares loggedInUser) -->
    <%@ include file="includes/navbar.jsp" %>

    <%
        Cart cart = (Cart) request.getAttribute("cart");
        if (cart == null) {
            cart = (Cart) session.getAttribute("cart");
        }

        // Preserve values if redirected back due to validation error, otherwise fallback to loggedInUser details
        String valFullName = request.getAttribute("paramFullName") != null ? (String) request.getAttribute("paramFullName") : (loggedInUser != null && loggedInUser.getUsername() != null ? loggedInUser.getUsername() : "");
        String valPhone = request.getAttribute("paramPhone") != null ? (String) request.getAttribute("paramPhone") : (loggedInUser != null && loggedInUser.getPhone() != null ? loggedInUser.getPhone() : "");
        String valStreet = request.getAttribute("paramStreet") != null ? (String) request.getAttribute("paramStreet") : (loggedInUser != null && loggedInUser.getAddress() != null ? loggedInUser.getAddress() : "");
        String valCity = request.getAttribute("paramCity") != null ? (String) request.getAttribute("paramCity") : "";
        String valPincode = request.getAttribute("paramPincode") != null ? (String) request.getAttribute("paramPincode") : "";
        
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>

    <div class="checkout-page-container">

        <!-- BACK TO CART -->
        <a href="<%= request.getContextPath() %>/cart" class="checkout-back-link">
            ← Back to Cart
        </a>

        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="checkout-error-banner" style="background:#ffdddd; color:#d8000c; padding:15px; border-radius:12px; margin-bottom:20px; font-weight:700; box-shadow:inset 2px 2px 5px rgba(0,0,0,0.1);">
                <%= errorMessage %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/checkout" method="post">

            <!-- 1. DELIVERY ADDRESS SECTION -->
            <div class="checkout-box">
                <h2>1. Delivery Address</h2>
                
                <div class="checkout-form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" class="checkout-input" placeholder="e.g., Arjun Reddy" value="<%= valFullName %>" required>
                </div>
                
                <div class="checkout-form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" class="checkout-input" placeholder="e.g., 9876501001" value="<%= valPhone %>" required>
                </div>
                
                <div class="checkout-form-group">
                    <label for="streetAddress">Delivery Address</label>
                    <textarea id="streetAddress" name="streetAddress" class="checkout-input" rows="3" placeholder="Flat/House No., Building, Street, Area" required><%= valStreet %></textarea>
                </div>
                
                <div class="checkout-form-row">
                    <div class="checkout-form-group flex-1">
                        <label for="city">City</label>
                        <input type="text" id="city" name="city" class="checkout-input" placeholder="e.g., Bengaluru" value="<%= valCity %>" required>
                    </div>
                    <div class="checkout-form-group flex-1">
                        <label for="pincode">PIN Code</label>
                        <input type="text" id="pincode" name="pincode" class="checkout-input" placeholder="e.g., 560001" value="<%= valPincode %>" required pattern="[0-9]{6}">
                    </div>
                </div>
            </div>

            <!-- 2. PAYMENT METHOD SECTION -->
            <div class="checkout-box">
                <h2>2. Payment Method</h2>
                <div class="payment-options">

                    <label class="payment-option-card">
                        <input type="radio" name="paymentMode" value="COD" checked onclick="togglePaymentFields()">
                        <div class="payment-card-content">
                            <span class="payment-title">💵 Cash on Delivery (COD)</span>
                            <span class="payment-sub">Pay in cash or UPI when your food arrives</span>
                        </div>
                    </label>

                    <label class="payment-option-card">
                        <input type="radio" name="paymentMode" value="CARD" onclick="togglePaymentFields()">
                        <div class="payment-card-content">
                            <span class="payment-title">💳 Credit / Debit Card</span>
                            <span class="payment-sub">Pay securely via Visa, MasterCard, or RuPay</span>
                        </div>
                    </label>

                    <label class="payment-option-card">
                        <input type="radio" name="paymentMode" value="UPI" onclick="togglePaymentFields()">
                        <div class="payment-card-content">
                            <span class="payment-title">📱 UPI / QR Code</span>
                            <span class="payment-sub">Google Pay, PhonePe, Paytm, or BHIM</span>
                        </div>
                    </label>

                </div>

                <!-- Dynamic Card Fields Container -->
                <div id="card-fields" style="display: none; margin-top: 20px; padding-top: 15px; border-top: 1px solid rgba(203, 206, 209, 0.6);">
                    <div class="checkout-form-group">
                        <label for="cardHolder">Cardholder Name</label>
                        <input type="text" id="cardHolder" name="cardHolder" class="checkout-input" placeholder="e.g., Arjun Reddy">
                    </div>
                    <div class="checkout-form-group">
                        <label for="cardNumber">Card Number</label>
                        <input type="text" id="cardNumber" name="cardNumber" class="checkout-input" placeholder="4111 2222 3333 4444" maxlength="16">
                    </div>
                    <div class="checkout-form-row">
                        <div class="checkout-form-group flex-1">
                            <label for="cardExpiry">Expiry Date</label>
                            <input type="text" id="cardExpiry" name="cardExpiry" class="checkout-input" placeholder="MM/YY" maxlength="5">
                        </div>
                        <div class="checkout-form-group flex-1">
                            <label for="cardCvv">CVV</label>
                            <input type="password" id="cardCvv" name="cardCvv" class="checkout-input" placeholder="123" maxlength="4">
                        </div>
                    </div>
                </div>

                <!-- Dynamic UPI Fields Container -->
                <div id="upi-fields" style="display: none; margin-top: 20px; padding-top: 15px; border-top: 1px solid rgba(203, 206, 209, 0.6);">
                    <div class="checkout-form-group">
                        <label for="upiId">UPI ID</label>
                        <input type="text" id="upiId" name="upiId" class="checkout-input" placeholder="e.g., username@oksbi">
                    </div>
                </div>
            </div>

            <!-- 3. REVIEW YOUR ORDER -->
            <div class="checkout-box">
                <h2>3. Review Your Items</h2>

                <%
                    if (cart != null && !cart.isEmpty()) {
                        Collection<CartItem> items = cart.getItems();
                        for (CartItem item : items) {
                %>

                    <div class="checkout-item-row">
                        <img src="<%= item.getMenu().getImagePath() %>"
                             alt="<%= item.getMenu().getItemName() %>"
                             class="checkout-item-img"
                             loading="lazy"
                             onerror="this.style.display='none';">

                        <div class="checkout-item-details">
                            <div class="checkout-item-name">
                                <%= item.getMenu().getItemName() %>
                            </div>
                            <div class="checkout-item-qty">
                                Quantity: <%= item.getQuantity() %>
                            </div>
                        </div>

                        <div class="checkout-item-price">
                            ₹<%= item.getSubtotal() %>
                        </div>
                    </div>

                <%
                        }
                    }
                %>
            </div>

            <!-- 4. ORDER SUMMARY & SUBMIT -->
            <div class="checkout-box">
                <h2>Order Summary</h2>

                <div class="summary-row">
                    <span>Item Subtotal</span>
                    <span>₹<%= cart != null ? cart.getTotalAmount() : 0 %></span>
                </div>

                <div class="summary-row">
                    <span>Delivery Fee</span>
                    <span>₹40</span>
                </div>

                <div class="total-row">
                    <span>Total Amount</span>
                    <span>₹<%= cart != null ? cart.getTotalAmount() + 40 : 40 %></span>
                </div>

                <button type="submit" class="confirm-order-btn">
                    Place Order & Pay 
                </button>
            </div>

        </form>

    </div>

    <!-- Toggle Script for Dynamic Payment Fields -->
    <script>
    function togglePaymentFields() {
        const cardFields = document.getElementById('card-fields');
        const upiFields = document.getElementById('upi-fields');
        const selectedMode = document.querySelector('input[name="paymentMode"]:checked').value;

        if (selectedMode === 'CARD') {
            cardFields.style.display = 'block';
            upiFields.style.display = 'none';
        } else if (selectedMode === 'UPI') {
            cardFields.style.display = 'none';
            upiFields.style.display = 'block';
        } else {
            cardFields.style.display = 'none';
            upiFields.style.display = 'none';
        }
    }
    // Trigger on page load to preserve state if validation redirects back
    window.onload = togglePaymentFields;
    </script>

    <!-- Modular Footer Fragment -->
    <%@ include file="includes/footer.jsp" %>

</body>
</html>