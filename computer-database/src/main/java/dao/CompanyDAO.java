package dao;

import model.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDAO extends JpaRepository<Company, Long> {

	public Company findByName(String name);
}
