package com.excilys.ui;

import java.util.List;

import com.excilys.model.Computer;

/**
 * The Class ComputerPager. Provides Pagination for computer listing.
 */
public class ComputerPager extends Pager {

	private List<Computer> entities;

	public ComputerPager() {
		entities = webservice.listComputers(page);
	}

	@Override
	public void next() {
		// Go to next page only if not at the end of pages
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
