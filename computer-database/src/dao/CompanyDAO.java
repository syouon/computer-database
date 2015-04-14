package dao;

import java.util.List;

import model.Company;

/**
 * The Interface CompanyDAO.
 */
public interface CompanyDAO {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Company> findAll();
}
