package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderItemDao;
import com.tap.model.OrderItem;
import com.tap.util.DBConnection;

public class OrderItemDaoImpl implements OrderItemDao {

    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO orderitem (orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?)";

    private static final String GET_ORDER_ITEM =
            "SELECT * FROM orderitem WHERE orderItemId = ?";

    private static final String GET_ALL_ORDER_ITEMS =
            "SELECT * FROM orderitem";

    private static final String GET_ORDER_ITEMS_BY_ORDER =
            "SELECT * FROM orderitem WHERE orderId = ?";

    private static final String UPDATE_ORDER_ITEM =
            "UPDATE orderitem SET orderId = ?, menuId = ?, quantity = ?, itemTotal = ? WHERE orderItemId = ?";

    private static final String DELETE_ORDER_ITEM =
            "DELETE FROM orderitem WHERE orderItemId = ?";

    @Override
    public void addOrderItem(OrderItem orderItem) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_ORDER_ITEM)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getMenuId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getItemTotal());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {

        OrderItem orderItem = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ORDER_ITEM)) {

            pstmt.setInt(1, orderItemId);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {

                orderItem = new OrderItem();

                orderItem.setOrderItemId(resultSet.getInt("orderItemId"));
                orderItem.setOrderId(resultSet.getInt("orderId"));
                orderItem.setMenuId(resultSet.getInt("menuId"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItem.setItemTotal(resultSet.getDouble("itemTotal"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_ORDER_ITEM)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getMenuId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getItemTotal());
            pstmt.setInt(5, orderItem.getOrderItemId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int orderItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_ITEM)) {

            pstmt.setInt(1, orderItemId);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> getAllOrderItems() {

        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ALL_ORDER_ITEMS)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                OrderItem orderItem = new OrderItem();

                orderItem.setOrderItemId(resultSet.getInt("orderItemId"));
                orderItem.setOrderId(resultSet.getInt("orderId"));
                orderItem.setMenuId(resultSet.getInt("menuId"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItem.setItemTotal(resultSet.getDouble("itemTotal"));

                orderItems.add(orderItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ORDER_ITEMS_BY_ORDER)) {

            pstmt.setInt(1, orderId);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                OrderItem orderItem = new OrderItem();

                orderItem.setOrderItemId(resultSet.getInt("orderItemId"));
                orderItem.setOrderId(resultSet.getInt("orderId"));
                orderItem.setMenuId(resultSet.getInt("menuId"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItem.setItemTotal(resultSet.getDouble("itemTotal"));

                orderItems.add(orderItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItems;
    }
}