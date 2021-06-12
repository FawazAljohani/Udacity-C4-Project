package com.udacity.c4.project.controller;

import com.udacity.c4.project.repositories.CartRepository;
import com.udacity.c4.project.repositories.ItemRepository;
import com.udacity.c4.project.repositories.UserRepository;
import com.udacity.c4.project.TestData;
import com.udacity.c4.project.TestUtils;
import com.udacity.c4.project.controllers.CartController;
import com.udacity.c4.project.model.persistence.Cart;
import com.udacity.c4.project.model.persistence.Item;
import com.udacity.c4.project.model.persistence.User;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);


    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
    }

    @Test
    public void add_to_cart() {
        User user = TestData.getDummyUser();
        Item item = TestData.getDummyItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername("foshe")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Cart> response = cartController.addTocart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Cart cartFromResponse = response.getBody();
        assertNotNull(cartFromResponse);
        assertNotNull(cartFromResponse.getItems());
        assertEquals("foshe", cartFromResponse.getUser().getUsername());
    }

    @Test
    public void add_to_cart_with_no_user() {
        ResponseEntity<Cart> response = cartController.addTocart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void add_to_card_with_no_item() {
        when(userRepository.findByUsername("foshe")).thenReturn(TestData.getDummyUser());
        when(itemRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Cart> response = cartController.addTocart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart() {
        User user = TestData.getDummyUser();
        Item item = TestData.getDummyItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername("foshe")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Cart> response = cartController.removeFromcart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        Cart cartFromResponse = response.getBody();
        assertNotNull(cartFromResponse);
        assertEquals(0, cartFromResponse.getItems().size());
    }

    @Test
    public void remove_from_cart_no_user() {
        ResponseEntity<Cart> response = cartController.removeFromcart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_no_item() {
        when(userRepository.findByUsername("foshe")).thenReturn(TestData.getDummyUser());
        when(itemRepository.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<Cart> response = cartController.removeFromcart(TestData.getCartRequest());
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }


}
