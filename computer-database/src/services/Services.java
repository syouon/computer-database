package services;

import java.sql.Connection;
import java.util.List;

import model.Company;
import model.Computer;

public abstract class Services {

	protected Connection conn;
	
	public Services(Connection conn) {
		this.conn = conn;
	}
	
	public abstract List<Computer> listComputers();
	public abstract List<Company> listCompanies();
	public abstract void showComputerDetails(long id);
	public abstract boolean addComputer(Computer computer);
	public abstract boolean updateComputer(long id);
	public abstract boolean deleteComputer(long id);
}
