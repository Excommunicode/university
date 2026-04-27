package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Order;
import com.example.university.models.Product;
import com.example.university.repositories.OrderRepository;
import com.example.university.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void createOrderResolvesProductAndSavesOrder() {
        Product product = new Product();
        product.setId(10L);

        Order order = new Order();
        order.setProductName("Book");
        order.setQuantity(2);
        order.setStatus("NEW");
        order.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order saved = service.createOrder(order);

        assertThat(saved.getProduct()).isEqualTo(product);
        verify(orderRepository).save(order);
    }

    @Test
    void createOrderThrowsWhenProductNotFound() {
        Product product = new Product();
        product.setId(99L);
        Order order = new Order();
        order.setProduct(product);

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createOrder(order));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrderThrowsWhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Order update = new Order();
        update.setProductName("Updated");
        update.setQuantity(1);
        update.setStatus("UPDATED");

        assertThrows(ResourceNotFoundException.class, () -> service.updateOrder(1L, update));
    }

    @Test
    void updateStatusUpdatesOrderAndSaves() {
        Order existing = new Order();
        existing.setId(5L);
        existing.setStatus("NEW");

        when(orderRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(orderRepository.save(existing)).thenReturn(existing);

        Order result = service.updateStatus(5L, "PAID");

        assertThat(result.getStatus()).isEqualTo("PAID");
        verify(orderRepository).save(existing);
    }

    @Test
    void deleteOrderThrowsWhenOrderNotFound() {
        when(orderRepository.existsById(777L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteOrder(777L));
        verify(orderRepository, never()).deleteById(777L);
    }
}
