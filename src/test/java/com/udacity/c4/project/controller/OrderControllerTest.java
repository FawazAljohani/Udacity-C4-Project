package com.udacity.c4.project.controller;


import com.udacity.c4.project.TestUtils;
import com.udacity.c4.project.controllers.OrderController;
import com.udacity.c4.project.model.persistence.Cart;
import com.udacity.c4.project.model.persistence.User;
import com.udacity.c4.project.model.persistence.UserOrder;
import com.udacity.c4.project.repositories.OrderRepository;
import com.udacity.c4.project.repositories.UserRepository;
import com.udacity.c4.project.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
    }

    @Test
    public void submit_order() {
        User user = TestData.getDummyUser();
        Cart cart = user.getCart();
        cart.setItems(TestData.getDummyItems());
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findByUsername("foshe")).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit("foshe");
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        UserOrder order = response.getBody();
        assertNotNull(order);
        assertEquals(1, order.getItems().size());
        assertEquals("foshe", order.getUser().getUsername());
    }

    @Test
    public void submit_order_with_nonexistent_user() {
        ResponseEntity<UserOrder> response = orderController.submit("foshe");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }


    @Test
    public void order_history() {
        User user = TestData.getDummyUser();
        Cart cart = user.getCart();
        cart.setItems(TestData.getDummyItems());
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findByUsername("foshe")).thenReturn(user);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("foshe");
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        List<UserOrder> orders = response.getBody();
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }

    @Test
    public void order_history_with_nonexistent_user() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("foshe");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }


}
