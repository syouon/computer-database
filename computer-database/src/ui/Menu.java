package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private List<Choice> choices;

	public Menu() {
		choices = new ArrayList<>();
	}

	public void addChoice(Choice choice) {
		choices.add(choice);
	}

	public void print() {
		for (int i = 0; i < choices.size(); i++) {
			System.out.println(i + ") " + choices.get(i));
		}
	}

	public Choice getUserChoice() {
		int input = -1;
		Scanner scanner = new Scanner(System.in);

		while (input < 0 || input >= choices.size()) {
			System.out.print("> ");
			try {
				input = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("O_o");
				continue;
			}
		}

		scanner.close();
		return choices.get(input);
	}
}