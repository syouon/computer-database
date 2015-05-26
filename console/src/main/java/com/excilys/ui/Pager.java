package com.excilys.ui;

import org.springframework.context.ApplicationContext;

import com.excilys.service.*;

public abstract class Pager {

	protected int entitiesNumber;
	protected int page;
	protected CompanyService companyService;
	protected ComputerService computerService;

	public Pager() {
		entitiesNumber = 10;
		page = 0;

		ApplicationContext context = Main.getContext();
		companyService = context.getBean(CompanyService.class);
		computerService = context.getBean(ComputerService.class);
	}

	public void next() {
		page++;
		refresh();
	}

	public void previous() {
		page--;
		if (page < 0) {
			page = 0;
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
