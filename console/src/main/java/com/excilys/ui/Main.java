package com.excilys.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/*
	 * Initialize Spring context. Because of that console is not a webapp and
	 * thus no web.xml is provided, we can't load Spring context another way.
	 */
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	/**
	 * Gets Spring context.
	 *
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	public static void main(String[] args) {

		// Initialize the command-line menu with its options and run it.
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
