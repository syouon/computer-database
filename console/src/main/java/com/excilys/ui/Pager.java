package com.excilys.ui;

/**
 * The Class Pager. Provides Pagination for listing.
 */
public abstract class Pager {

	// Number of elements by page
	protected int entitiesNumber;

	// The number page
	protected int page;

	protected RestWebService webservice;

	public Pager() {
		// Default values
		entitiesNumber = 10;
		page = 1;

		webservice = RestWebService.INSTANCE;
	}

	/**
	 * Gets next page.
	 */
	public void next() {
		page++;
		refresh();
	}

	/**
	 * Gets previous page.
	 */
	public void previous() {
		page--;
		if (page < 1) {
			page = 1;
		}
		refresh();
	}

	/**
	 * Wait for action. An action can be:
	 * - p: previous page
	 * - n: next page
	 * - q: quit the pagination
	 */
	public void waitForAction() {
		loop: while (true) {
			System.out.println("<< p,  q (quit)  , n >>");
			String input = Menu.getScanner().nextLine();

			switch (input) {
			case "n":
				next();
				print();
				break;
			case "p":
				previous();
				print();
				break;
			case "q":
				break loop;
			}
		}
	}

	/**
	 * Refresh elements of the page
	 */
	public abstract void refresh();

	/**
	 * Prints the menu.
	 */
	public abstract void print();
}
