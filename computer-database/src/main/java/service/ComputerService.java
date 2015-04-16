package service;

import java.util.List;

import dao.ConcreteComputerDAO;
import model.Computer;

public enum ComputerService {
	INSTANCE;

	public List<Computer> listComputers(int start, int range) {
		return ConcreteComputerDAO.getInstance().findAll(start, range);
	}

	public Computer showComputerDetails(long id) {
		return ConcreteComputerDAO.getInstance().find(id);
	}

	public boolean addComputer(Computer computer) {
		return ConcreteComputerDAO.getInstance().create(computer);
	}

	public boolean deleteComputer(long id) {
		return ConcreteComputerDAO.getInstance().delete(id);
	}

	public boolean updateComputer(Computer computer) {
		return ConcreteComputerDAO.getInstance().update(computer);
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}
}
