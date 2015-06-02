package com.excilys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

/**
 * The Class ComputerServiceImpl. Provides methods to retrieve computer
 * information.
 */
@Service
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDAO dao;

	public List<Computer> listComputers(String regex, int start, int range,
			String orderBy, boolean desc) {

		Sort sort = null;
		if (desc) {
			sort = new Sort(Sort.Direction.DESC, orderBy);
		} else {
			sort = new Sort(Sort.Direction.ASC, orderBy);
		}
		return dao.findByNameContaining(regex, new PageRequest(start, range,
				sort));
	}

	public List<Computer> listComputers(String regex, int start, int range) {
		return dao.findByNameContaining(regex, new PageRequest(start, range));
	}

	public List<Computer> listComputers(int start, int range, String orderBy,
			boolean desc) {

		Sort sort = null;
		if (desc) {
			sort = new Sort(Sort.Direction.DESC, orderBy);
		} else {
			sort = new Sort(Sort.Direction.ASC, orderBy);
		}
		return dao.findAll(new PageRequest(start, range, sort)).getContent();
	}

	public List<Computer> listComputers(int start, int range) {
		Page<Computer> page = dao.findAll(new PageRequest(start, range));
		return page.getContent();
	}

	public List<Computer> listComputers() {
		return dao.findAll();
	}

	public Computer showComputerDetails(long id) {
		return dao.findOne(id);
	}

	public long addComputer(Computer computer) {
		computer = dao.save(computer);
		return computer.getId();
	}

	public void deleteComputer(long id) {
		dao.delete(id);
	}

	public void updateComputer(Computer computer) {
		dao.saveAndFlush(computer);
	}

	public int count() {
		return (int) dao.count();
	}

	public int countSearchResult(String regex) {
		return dao.countByNameContaining(regex);
	}
}
