package service;

import java.util.List;

import model.Computer;
import dao.ComputerDAO;
import dao.ComputerDAOImpl;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	private ComputerDAO dao;

	private ComputerServiceImpl() {
		dao = ComputerDAOImpl.getInstance();
	}

	public List<Computer> listComputers(int start, int range) {
		return dao.findAll(start, range);
	}

	public List<Computer> listComputers() {
		return dao.findAll();
	}

	public Computer showComputerDetails(long id) {
		return dao.find(id);
	}

	public long addComputer(Computer computer) {
		return dao.create(computer);
	}

	public boolean deleteComputer(long id) {
		return dao.delete(id);
	}

	public boolean updateComputer(Computer computer) {
		return dao.update(computer);
	}

	public int count() {
		return dao.count();
	}
	
	public static ComputerServiceImpl getInstance() {
		return INSTANCE;
	}
}