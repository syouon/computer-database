package com.excilys.ui;

public abstract class Pager {

	protected int entitiesNumber;
	protected int page;
	protected RestWebService webservice;

	public Pager() {
		entitiesNumber = 10;
		page = 1;

		webservice = new RestWebService();
	}

	public void next() {
		page++;
		refresh();
	}

	public void previous() {
		page--;
		if (page < 1) {
			page = 1;
		}
		refresh();
	}

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

	public abstract void refresh();

	public abstract void print();
}
