package main;

import ui.AddComputerChoice;
import ui.DeleteComputerChoice;
import ui.ListCompaniesChoice;
import ui.ListComputersChoice;
import ui.Menu;
import ui.QuitChoice;
import ui.ShowDetailsChoice;
import ui.UpdateComputerChoice;

public class Main {

	public static void main(String[] args) {

		Menu menu = new Menu();
		menu.addChoice(new QuitChoice());
		menu.addChoice(new ListComputersChoice());
		menu.addChoice(new ListCompaniesChoice());
		menu.addChoice(new ShowDetailsChoice());
		menu.addChoice(new AddComputerChoice());
		menu.addChoice(new UpdateComputerChoice());
		menu.addChoice(new DeleteComputerChoice());
		menu.loop();
	}
}
