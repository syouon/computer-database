package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;

public interface CompanyService {

	public List<Company> listCompanies();

	public List<Company> listCompanies(int start, int range);

	public Company find(long id);

	public Company find(String name);

	public boolean exists(Company company);

	public boolean deleteCompany(long id);
}
