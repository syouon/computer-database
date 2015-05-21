package com.excilys.dao;

import org.springframework.data.repository.CrudRepository;
import com.excilys.model.User;

public interface UserDAO extends CrudRepository<User, String> {

	public User findByUsername(String username);
}
