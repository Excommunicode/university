package com.example.university.services.contract;

import com.example.university.models.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order findOrder(Long id);

    List<Order> getAllOrders();

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

    Order updateStatus(Long id, String status);

    List<Order> getOrdersByStatus(String status);
}
