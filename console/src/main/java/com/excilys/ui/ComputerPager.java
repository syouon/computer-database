package com.excilys.ui;

import java.util.List;

import com.excilys.model.Computer;

public class ComputerPager extends Pager {

	private List<Computer> entities;

	public ComputerPager() {
		super();
		entities = webservice.listComputers(page);
	}

	@Override
	public void next() {
		if (entities.size() == entitiesNumber) {
			super.next();
		}
	}

	@Override
	public void refresh() {
		entities = webservice.listComputers(page);
	}

	@Override
	public void print() {
		for (Computer entity : entities) {
			System.out.println(entity);
		}
	}
}
