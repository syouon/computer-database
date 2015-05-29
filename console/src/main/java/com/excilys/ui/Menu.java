package com.excilys.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The Class Menu. Represents all the menu's options.
 */
public class Menu {

	private List<Choice> choices;

	// To read user's input in command-line
	private static Scanner scanner;

	public Menu() {
		choices = new ArrayList<>();
		scanner = new Scanner(System.in);
	}

	/**
	 * Gets the scanner to read user's input.
	 * 
	 * @return the scanner
	 */
	public static Scanner getScanner() {
		return scanner;
	}

	/**
	 * Adds a choice to the menu.
	 * 
	 * @param choice
	 *            the choice to add
	 */
	public void addChoice(Choice choice) {
		choices.add(choice);
	}

	/**
	 * Prints the menu.
	 */
	private void print() {
		for (int i = 0; i < choices.size(); i++) {
			System.out.println(i + ") " + choices.get(i));
		}
	}

	/**
	 * Gets the user choice.
	 * 
	 * @return the user choice
	 */
	private Choice getUserChoice() {
		int choice = -1;

		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();
			boolean match = Pattern.matches("\\d*", input);
			// if input is a number
			if (match) {
				choice = Integer.parseInt(input);
				if (choice >= 0 && choice < choices.size()) {
					break;
				}
			}

			System.out.println("O_o");
		}

		return choices.get(choice);
	}

	/**
	 * Run the menu
	 */
	public void loop() {
		Choice choice = null;
		do {
			print();
			choice = getUserChoice();
			choice.execute();
		} while (!(choice instanceof QuitChoice));
	}
}
