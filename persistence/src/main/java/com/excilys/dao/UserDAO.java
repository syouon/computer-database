package com.excilys.dao;

import org.springframework.data.repository.CrudRepository;
import com.excilys.model.User;

/**
 * Spring Data CrudRepository interface provides basic CRUD methods.
 */
public interface UserDAO extends CrudRepository<User, String> {

	public User findByUsername(String username);
}
