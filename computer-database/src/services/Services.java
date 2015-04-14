package services;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import model.Company;
import model.Computer;

/**
 * The Class Services.
 */
public abstract class Services {

	/** The conn. */
	protected Connection conn;

	/**
	 * Instantiates a new services.
	 *
	 * @param conn the conn
	 */
	public Services(Connection conn) {
		this.conn = conn;
	}

	/**
	 * List computers.
	 *
	 * @return the list
	 */
	public abstract List<Computer> listComputers();

	/**
	 * List companies.
	 *
	 * @return the list
	 */
	public abstract List<Company> listCompanies();

	/**
	 * Show computer details.
	 *
	 * @param id the id
	 */
	public abstract void showComputerDetails(long id);

	/**
	 * Adds the computer.
	 *
	 * @param computer the computer
	 * @return true, if successful
	 */
	public abstract boolean addComputer(Computer computer);

	/**
	 * Update introduction date.
	 *
	 * @param id the id
	 * @param introduced the introduced
	 * @return true, if successful
	 */
	public abstract boolean updateIntroductionDate(long id, Date introduced);

	/**
	 * Update discontinuation date.
	 *
	 * @param id the id
	 * @param discontinued the discontinued
	 * @return true, if successful
	 */
	public abstract boolean updateDiscontinuationDate(long id, Date discontinued);

	/**
	 * Update company.
	 *
	 * @param id the id
	 * @param company the company
	 * @return true, if successful
	 */
	public abstract boolean updateCompany(long id, Company company);

	/**
	 * Delete computer.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public abstract boolean deleteComputer(long id);
}
