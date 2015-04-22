package service;

import java.util.List;

import model.Computer;

public interface ComputerService {

	public List<Computer> listComputers(int start, int range);

	public List<Computer> listComputers();

	public Computer showComputerDetails(long id);

	public long addComputer(Computer computer);

	public boolean deleteComputer(long id);

	public boolean updateComputer(Computer computer);

	public List<Computer> search(String regex, int start, int range);

	public int count();
	
	public int countSearchResult(String regex);
}
