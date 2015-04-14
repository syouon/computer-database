package services;

import java.util.List;

import model.Computer;

public enum ComputerService {
	INSTANCE;
	
	public List<Computer> listComputers() {
		return null;
	}
	
	public Computer showComputerDetails(long id) {
		return null;
	}
	
	public boolean addComputer(Computer computer) {
		return false;
	}
	
	public boolean deleteComputer(Computer computer) {
		return false;
	}
	
	public boolean updateComputer(Computer computer) {
		return false;
	}
	
	public static ComputerService getInstance() {
		return INSTANCE;
	}
}
