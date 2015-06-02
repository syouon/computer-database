package com.excilys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;

/**
 * The Class CompanyServiceImpl. Provides methods to retrieve company
 * information.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private ComputerDAO computerDAO;

	public List<Company> listCompanies(int start, int range) {
		Page<Company> page = companyDAO.findAll(new PageRequest(start, range));
		return page.getContent();
	}

	public List<Company> listCompanies() {
		return companyDAO.findAll();
	}

	public Company find(long id) {
		return companyDAO.findOne(id);
	}

	public Company find(String name) {
		return companyDAO.findByName(name);
	}

	public boolean exists(Company company) {
		return (companyDAO.findByName(company.getName()) != null);
	}

	@Transactional
	public boolean deleteCompany(long id) {
		computerDAO.deleteByCompanyId(id);
		companyDAO.delete(id);
		return true;
	}
}