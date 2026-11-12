package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderDao;
import com.tap.model.Order;
import com.tap.util.DBConnection;

public class OrderDaoImpl implements OrderDao {

    private static final String INSERT_ORDER =
            "INSERT INTO orders (restaurantId, userId, totalAmount, status, paymentMode) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ORDER =
            "SELECT * FROM orders WHERE orderId = ?";

    private static final String GET_ALL_ORDERS =
            "SELECT * FROM orders";

    private static final String GET_ORDERS_BY_USER =
            "SELECT * FROM orders WHERE userId = ?";

    private static final String UPDATE_ORDER =
            "UPDATE orders SET restaurantId = ?, userId = ?, totalAmount = ?, status = ?, paymentMode = ? WHERE orderId = ?";

    private static final String DELETE_ORDER =
            "DELETE FROM orders WHERE orderId = ?";


//    @Override
//    public void addOrder(Order order) {
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement pstmt = con.prepareStatement(INSERT_ORDER)) {
//
//            pstmt.setInt(1, order.getRestaurantId());
//            pstmt.setInt(2, order.getUserId());
//            pstmt.setDouble(3, order.getTotalAmount());
//            pstmt.setString(4, order.getStatus());
//            pstmt.setString(5, order.getPaymentMode());
//
//            pstmt.executeUpdate();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//    }
    
    
    
    @Override
    public void addOrder(Order order) {
        // Updated to capture the auto-generated orderId and assign it back to the order object
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getRestaurantId());
            pstmt.setInt(2, order.getUserId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMode());

            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Automatically sets the generated orderId into your Order object!
                        order.setOrderId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to save all items in the cart to the orderitem table
    public void addOrderItems(int orderId, com.tap.model.Cart cart) {
        String insertItemQuery = "INSERT INTO orderitem (orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertItemQuery)) {

            for (com.tap.model.CartItem item : cart.getItems()) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, item.getMenu().getMenuId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getSubtotal());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Order getOrder(int orderId) {

        Order order = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ORDER)) {

            pstmt.setInt(1, orderId);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {

                order = new Order();

                order.setOrderId(resultSet.getInt("orderId"));
                order.setRestaurantId(resultSet.getInt("restaurantId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setTotalAmount(resultSet.getDouble("totalAmount"));
                order.setStatus(resultSet.getString("status"));
                order.setPaymentMode(resultSet.getString("paymentMode"));
                order.setOrderDate(resultSet.getTimestamp("orderDate"));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return order;
    }


    @Override
    public void updateOrder(Order order) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_ORDER)) {

            pstmt.setInt(1, order.getRestaurantId());
            pstmt.setInt(2, order.getUserId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMode());
            pstmt.setInt(6, order.getOrderId());

            pstmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    @Override
    public void deleteOrder(int orderId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER)) {

            pstmt.setInt(1, orderId);

            pstmt.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_ORDERS)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("orderId"));
                order.setRestaurantId(resultSet.getInt("restaurantId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setTotalAmount(resultSet.getDouble("totalAmount"));
                order.setStatus(resultSet.getString("status"));
                order.setPaymentMode(resultSet.getString("paymentMode"));
                order.setOrderDate(resultSet.getTimestamp("orderDate"));

                orders.add(order);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return orders;
    }


    @Override
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ORDERS_BY_USER)) {

            pstmt.setInt(1, userId);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("orderId"));
                order.setRestaurantId(resultSet.getInt("restaurantId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setTotalAmount(resultSet.getDouble("totalAmount"));
                order.setStatus(resultSet.getString("status"));
                order.setPaymentMode(resultSet.getString("paymentMode"));
                order.setOrderDate(resultSet.getTimestamp("orderDate"));

                orders.add(order);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return orders;
    }

}