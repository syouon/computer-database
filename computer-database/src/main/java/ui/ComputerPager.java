package ui;

import java.util.List;

import service.ComputerService;
import model.Computer;

public class ComputerPager extends Pager {

	private List<Computer> entities;

	public ComputerPager() {
		super();
		entities = ComputerService.getInstance().listComputers(start,
				entitiesNumber);
	}

	@Override
	public void next() {
		if (entities.size() == entitiesNumber) {
			super.next();
		}
	}

	@Override
	public void refresh() {
		entities = ComputerService.getInstance().listComputers(start,
				entitiesNumber);
	}

	@Override
	public void print() {
		for (Computer entity : entities) {
			System.out.println(entity);
		}
	}
}
