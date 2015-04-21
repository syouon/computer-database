package service;

import java.util.List;

import model.Company;

public interface CompanyService {

	public List<Company> listCompanies();

	public List<Company> listCompanies(int start, int range);

	public Company find(long id);
}
