package com.tap.dao;

import com.tap.model.Cart;
import com.tap.model.Order;
import java.util.List;

public interface OrderDao {

    void addOrder(Order order);

    Order getOrder(int orderId);
    
    void updateOrder(Order order);

    void deleteOrder(int orderId);
    
    List<Order> getAllOrders();
    
    List<Order> getOrdersByUserId(int userId);

}