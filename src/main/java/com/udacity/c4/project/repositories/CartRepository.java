package com.udacity.c4.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.c4.project.model.persistence.Cart;
import com.udacity.c4.project.model.persistence.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
