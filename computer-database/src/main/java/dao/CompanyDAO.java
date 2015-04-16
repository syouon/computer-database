package dao;

import java.util.List;

import model.Company;

/**
 * The Interface CompanyDAO.
 */
public interface CompanyDAO {

	public Company find(long id);

	/**
	 * Find all.
	 * 
	 * @return the list
	 */
	public List<Company> findAll(int start, int range);

	public List<Company> findAll();

	public boolean exists(Company company);
}
