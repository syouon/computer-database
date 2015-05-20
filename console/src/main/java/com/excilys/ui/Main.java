package com.excilys.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	public static ApplicationContext getContext() {
		return context;
	}

	public static void main(String[] args) {

		Menu menu = new Menu();
		menu.addChoice(new QuitChoice());
		menu.addChoice(new ListComputersChoice());
		menu.addChoice(new ListCompaniesChoice());
		menu.addChoice(new ShowDetailsChoice());
		menu.addChoice(new AddComputerChoice());
		menu.addChoice(new UpdateComputerChoice());
		menu.addChoice(new DeleteComputerChoice());
		menu.addChoice(new DeleteCompanyChoice());
		menu.loop();
	}
}
