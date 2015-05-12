package service;

import java.util.List;

import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ComputerDAO;

@Service
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDAO dao;

	public List<Computer> listComputers(String regex, int start, int range,
			String orderBy, boolean desc) {
		return dao.findAll(regex, start, range, orderBy, desc);
	}

	public List<Computer> listComputers(String regex, int start, int range) {
		return dao.findAll(regex, start, range);
	}

	public List<Computer> listComputers(int start, int range, String orderBy,
			boolean desc) {
		return dao.findAll(start, range, orderBy, desc);
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
		dao.create(computer);
		return computer.getId();
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

	public int countSearchResult(String regex) {
		return dao.countSearchResult(regex);
	}
}
