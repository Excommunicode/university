package com.example.university.services.contract;

import com.example.university.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order createOrder(Order order);

    Order findOrder(Long id);

    Page<Order> getAllOrders(Pageable pageable);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

    Order updateStatus(Long id, String status);

    Page<Order> getOrdersByStatus(String status, Pageable pageable);
}
