package ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.CompanyService;
import service.CompanyServiceImpl;
import service.ComputerService;
import service.ComputerServiceImpl;

public abstract class Pager {

	protected int entitiesNumber;
	protected int start;
	protected CompanyService companyService;
	protected ComputerService computerService;

	public Pager() {
		entitiesNumber = 10;
		start = 0;

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		companyService = context.getBean(CompanyServiceImpl.class);
		computerService = context.getBean(ComputerServiceImpl.class);
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
