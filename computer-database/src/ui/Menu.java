package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The Class Menu.
 */
public class Menu {

	/** The choices. */
	private List<Choice> choices;
	
	/** The scanner. */
	private static Scanner scanner;

	/**
	 * Instantiates a new menu.
	 */
	public Menu() {
		choices = new ArrayList<>();
		scanner = new Scanner(System.in);
	}

	/**
	 * Gets the scanner.
	 *
	 * @return the scanner
	 */
	public static Scanner getScanner() {
		return scanner;
	}

	/**
	 * Adds the choice.
	 *
	 * @param choice the choice
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
	 * Menu loop.
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