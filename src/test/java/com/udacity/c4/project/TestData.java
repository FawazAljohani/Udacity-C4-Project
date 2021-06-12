package com.udacity.c4.project;

import com.udacity.c4.project.model.persistence.Cart;
import com.udacity.c4.project.model.persistence.Item;
import com.udacity.c4.project.model.persistence.User;
import com.udacity.c4.project.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static User getDummyUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("foshe");
        user.setPassword("password");
        user.setCart(emptyCart());
        return user;
    }

    public static Item getDummyItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("DummyItem");
        item.setPrice(BigDecimal.valueOf(2.59));
        item.setDescription("This is a Dummy Item");
        return item;
    }

    public static List<Item> getDummyItems() {

        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        item.setId(1L);
        item.setName("DummyItem");
        item.setPrice(BigDecimal.valueOf(2.59));
        item.setDescription("This is a Dummy Item");
        itemList.add(item);
        return itemList;
    }

    public static ModifyCartRequest getCartRequest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("foshe");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        return modifyCartRequest;
    }

    public static Cart emptyCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(null);
        cart.setItems(new ArrayList<Item>());
        cart.setTotal(BigDecimal.valueOf(0.0));
        return cart;
    }
}
