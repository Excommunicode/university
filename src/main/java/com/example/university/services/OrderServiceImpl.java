package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Order;
import com.example.university.models.Product;
import com.example.university.repositories.OrderRepository;
import com.example.university.repositories.ProductRepository;
import com.example.university.services.contract.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        log.info("createOrder called: product='{}', quantity={}", order.getProductName(), order.getQuantity());
        order.setProduct(resolveProduct(order));
        return orderRepository.save(order);
    }

    @Override
    public Order findOrder(Long id) {
        log.info("findOrder called: id={}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        log.info("getAllOrders called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order order) {
        log.info("updateOrder called: id={}, status='{}'", id, order.getStatus());
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        existing.setProductName(order.getProductName());
        existing.setQuantity(order.getQuantity());
        existing.setStatus(order.getStatus());
        existing.setProduct(resolveProduct(order));
        return orderRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("deleteOrder called: id={}", id);
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Order updateStatus(Long id, String status) {
        log.info("updateStatus called: id={}, status='{}'", id, status);
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        existing.setStatus(status);
        return orderRepository.save(existing);
    }

    @Override
    public Page<Order> getOrdersByStatus(String status, Pageable pageable) {
        log.info("getOrdersByStatus called: status='{}', page={}, size={}", status, pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findByStatus(status, pageable);
    }

    private Product resolveProduct(Order order) {
        if (order.getProduct() == null || order.getProduct().getId() == null) {
            return null;
        }

        return productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + order.getProduct().getId()));
    }
}
