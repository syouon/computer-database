package service;

import java.util.List;

import model.Computer;

public interface ComputerService {

	public List<Computer> listComputers(String regex, int start, int range,
			String orderBy, boolean desc);

	public List<Computer> listComputers(String regex, int start, int range);

	public List<Computer> listComputers(int start, int range, String orderBy,
			boolean desc);

	public List<Computer> listComputers(int start, int range);

	public List<Computer> listComputers();

	public Computer showComputerDetails(long id);

	public long addComputer(Computer computer);

	public boolean deleteComputer(long id);

	public boolean updateComputer(Computer computer);

	public int count();

	public int countSearchResult(String regex);
}
