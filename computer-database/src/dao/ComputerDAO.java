package dao;

import java.util.List;

import model.Computer;

/**
 * The Interface ComputerDAO.
 */
public interface ComputerDAO {

	/**
	 * Creates the.
	 *
	 * @param computer
	 *            the computer
	 * @return true, if successful
	 */
	public boolean create(Computer computer);

	/**
	 * Delete.
	 *
	 * @param computer
	 *            the computer
	 * @return true, if successful
	 */
	public boolean delete(long id);

	/**
	 * Update.
	 *
	 * @param computer
	 *            the computer
	 * @return true, if successful
	 */
	public boolean update(Computer computer);

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the computer
	 */
	public Computer find(long id);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Computer> findAll();
}
