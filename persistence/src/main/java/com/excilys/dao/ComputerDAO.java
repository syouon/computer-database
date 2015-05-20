package com.excilys.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.model.Computer;

public interface ComputerDAO extends JpaRepository<Computer, Long> {

	// TODO count by name OR company name
	public int countByNameContaining(String name);

	public void deleteByCompanyId(long companyId);

	// TODO research by name OR company name
	public List<Computer> findByNameContaining(String regex, Pageable page);
}
