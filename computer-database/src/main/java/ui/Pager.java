package ui;

import service.CompanyService;
import service.ComputerService;

public abstract class Pager {

	protected int entitiesNumber;
	protected int start;
	protected CompanyService companyService;
	protected ComputerService computerService;

	public Pager() {
		entitiesNumber = 10;
		start = 0;
	}

	public void next() {
		start += entitiesNumber;
		refresh();
	}

	public void previous() {
		start -= entitiesNumber;
		if (start < 0) {
			start = 0;
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
