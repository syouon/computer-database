package service;

import java.util.List;

import dao.ComputerDAOImpl;
import model.Computer;

public enum ComputerService {
	INSTANCE;

	public List<Computer> listComputers(int start, int range) {
		return ComputerDAOImpl.getInstance().findAll(start, range);
	}

	public List<Computer> listComputers() {
		return ComputerDAOImpl.getInstance().findAll();
	}

	public Computer showComputerDetails(long id) {
		return ComputerDAOImpl.getInstance().find(id);
	}

	public boolean addComputer(Computer computer) {
		return ComputerDAOImpl.getInstance().create(computer);
	}

	public boolean deleteComputer(long id) {
		return ComputerDAOImpl.getInstance().delete(id);
	}

	public boolean updateComputer(Computer computer) {
		return ComputerDAOImpl.getInstance().update(computer);
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}
}
