package ui;

import java.util.List;

import service.ComputerServiceImpl;
import model.Computer;

public class ComputerPager extends Pager {

	private List<Computer> entities;

	public ComputerPager() {
		super();
		entities = ComputerServiceImpl.getInstance().listComputers(start,
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
		entities = ComputerServiceImpl.getInstance().listComputers(start,
				entitiesNumber);
	}

	@Override
	public void print() {
		for (Computer entity : entities) {
			System.out.println(entity);
		}
	}
}
