package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		int input = -1;

		while (input < 0 || input >= choices.size()) {
			System.out.print("> ");
			try {
				input = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("O_o");
				continue;
			}
		}

		return choices.get(input);
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