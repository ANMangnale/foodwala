<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<footer class="footer-container">
    <div class="footer-content">
        <div class="footer-brand">
            <div class="footer-logo">
                Food<span>Wala</span>
            </div>
            <p class="footer-tagline">
                Delicious meals delivered to your doorstep with a smooth, tactile experience.
            </p>
        </div>

        <div class="footer-links">
            <div class="footer-column">
                <h4>Quick Links</h4>
                <a href="<%= request.getContextPath() %>/restaurant">Restaurants</a>
                <a href="<%= request.getContextPath() %>/cart">My Cart</a>
                <a href="<%= request.getContextPath() %>/orders">Order History</a>
            </div>

            <div class="footer-column">
                <h4>Support</h4>
                <a href="#">Help & Support</a>
                <a href="#">Terms & Conditions</a>
                <a href="#">Privacy Policy</a>
            </div>
        </div>
    </div>

    <div class="footer-bottom">
        <p>Made with ❤️ by <span>FoodWala</span> &copy; <%= java.time.Year.now().getValue() %>. All rights reserved.</p>
    </div>
</footer>

<style>
.footer-container {
    width: 100%;
    background-color: var(--bg-main, #e4e9f0);
    box-shadow: var(--neu-outset-sm, 4px 4px 8px #cbced1, -4px -4px 8px #ffffff);
    margin-top: 60px;
    padding: 40px 6% 20px;
}

.footer-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 30px;
    padding-bottom: 30px;
    border-bottom: 1px solid rgba(203, 206, 209, 0.4);
}

.footer-brand {
    max-width: 350px;
}

.footer-logo {
    font-size: 26px;
    font-weight: 800;
    color: var(--text-main, #2d3748);
    margin-bottom: 10px;
}

.footer-logo span {
    color: var(--primary, #ff5200);
}

.footer-tagline {
    font-size: 14px;
    color: var(--text-muted, #718096);
    line-height: 1.6;
}

.footer-links {
    display: flex;
    gap: 60px;
}

.footer-column {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.footer-column h4 {
    font-size: 16px;
    font-weight: 700;
    color: var(--text-main, #2d3748);
    margin-bottom: 5px;
}

.footer-column a {
    text-decoration: none;
    font-size: 14px;
    color: var(--text-muted, #718096);
    transition: var(--transition, all 0.25s ease);
}

.footer-column a:hover {
    color: var(--primary, #ff5200);
    transform: translateX(3px);
}

.footer-bottom {
    text-align: center;
    padding-top: 20px;
    font-size: 13px;
    color: var(--text-muted, #718096);
}

.footer-bottom span {
    color: var(--primary, #ff5200);
    font-weight: 700;
}

@media (max-width: 768px) {
    .footer-content {
        flex-direction: column;
    }
    .footer-links {
        gap: 30px;
    }
}
</style>