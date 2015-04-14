package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

	private List<Choice> choices;
	private static Scanner scanner;

	public Menu() {
		choices = new ArrayList<>();
		scanner = new Scanner(System.in);
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public void addChoice(Choice choice) {
		choices.add(choice);
	}

	private void print() {
		for (int i = 0; i < choices.size(); i++) {
			System.out.println(i + ") " + choices.get(i));
		}
	}

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

	public void loop() {
		Choice choice = null;
		do {
			print();
			choice = getUserChoice();
			choice.execute();
		} while (!(choice instanceof QuitChoice));
	}
}