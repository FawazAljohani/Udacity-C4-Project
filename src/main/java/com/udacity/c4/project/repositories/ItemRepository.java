package com.udacity.c4.project.repositories;

import java.util.List;

import com.udacity.c4.project.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);

}
