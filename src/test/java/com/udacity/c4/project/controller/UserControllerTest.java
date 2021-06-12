package com.udacity.c4.project.controller;

import com.udacity.c4.project.TestUtils;
import com.udacity.c4.project.controllers.UserController;
import com.udacity.c4.project.model.persistence.User;
import com.udacity.c4.project.model.requests.CreateUserRequest;
import com.udacity.c4.project.repositories.CartRepository;
import com.udacity.c4.project.repositories.UserRepository;
import com.udacity.c4.project.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception {
        when(encoder.encode("pas$word")).thenReturn("StubbedPassword");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("foshe");
        userRequest.setPassword("pas$word");
        userRequest.setConfirmPassword("pas$word");
        final ResponseEntity<User> response = userController.createUser(userRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        User createdUser = response.getBody();
        assertNotNull(createdUser);
        assertEquals(0, createdUser.getId());
        assertEquals("foshe", createdUser.getUsername());
        assertEquals("StubbedPassword", createdUser.getPassword());
    }

    @Test
    public void get_user_details() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(TestData.getDummyUser()));
        ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        User searchedUser = response.getBody();
        assertNotNull(searchedUser);
        assertEquals(1L, searchedUser.getId());
        assertEquals("foshe", searchedUser.getUsername());
        assertEquals("password", searchedUser.getPassword());
    }

    @Test
    public void get_user_by_username() {
        when(userRepository.findByUsername("foshe")).thenReturn(TestData.getDummyUser());
        ResponseEntity<User> response = userController.findByUserName("foshe");
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        User searchedUser = response.getBody();
        assertNotNull(searchedUser);
        assertEquals(1L, searchedUser.getId());
        assertEquals("foshe", searchedUser.getUsername());
        assertEquals("password", searchedUser.getPassword());
    }

    @Test
    public void get_user_details_by_invalidId() {
        ResponseEntity<User> response = userController.findById(2L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void get_user_details_by_invalid_username() {
        ResponseEntity<User> response = userController.findByUserName("invuser");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }



}
