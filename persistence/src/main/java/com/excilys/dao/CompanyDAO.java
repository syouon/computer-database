package com.excilys.dao;

import com.excilys.model.Company;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA JpaRepository interface provides some basic methods to manage
 * a database
 */
public interface CompanyDAO extends JpaRepository<Company, Long> {

	public Company findByName(String name);
}