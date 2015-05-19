package dao;

import java.util.List;

import model.Computer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComputerDAO extends JpaRepository<Computer, Long> {

	@Query("select count(c) from Computer c where c.name like %?1% or c.company.name like %?1%")
	public int countByNameContaining(String name);

	public void deleteByCompanyId(long companyId);

	@Query("select c from Computer c where c.name like %?1% or c.company.name like %?1%")
	public List<Computer> findByNameContaining(String regex, Pageable page);
}