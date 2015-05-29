package com.excilys.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excilys.model.Computer;

public interface ComputerDAO extends JpaRepository<Computer, Long> {

	@Query("select count(c) from Computer c left join c.company co where c.name like %:name% or co.name like %:name%")
	public int countByNameContaining(@Param("name") String name);

	public void deleteByCompanyId(long companyId);

	@Query("select c from Computer c left join c.company co where c.name like %:name% or co.name like %:name%")
	public List<Computer> findByNameContaining(@Param("name") String regex,
			Pageable page);
}
