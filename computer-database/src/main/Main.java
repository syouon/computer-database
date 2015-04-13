package main;

import services.Services;
import ui.AddComputerChoice;
import ui.DeleteComputerChoice;
import ui.ListCompaniesChoice;
import ui.ListComputersChoice;
import ui.Menu;
import ui.QuitChoice;
import ui.ShowDetailsChoice;
import ui.UpdateCompanyChoice;
import ui.UpdateDiscontinuationChoice;
import ui.UpdateIntroductionChoice;
import dbconnection.DatabaseConnection;
import dbconnection.MySQLConnection;

public class Main {

	public static void main(String[] args) {

		DatabaseConnection conn = MySQLConnection.getInstance();
		Services services = conn.connect();

		if (services == null) {
			System.out.println("Connection failed!");
			return;
		}

		Menu menu = new Menu();
		menu.addChoice(new QuitChoice());
		menu.addChoice(new ListComputersChoice(services));
		menu.addChoice(new ListCompaniesChoice(services));
		menu.addChoice(new ShowDetailsChoice(services));
		menu.addChoice(new AddComputerChoice(services));
		menu.addChoice(new UpdateIntroductionChoice(services));
		menu.addChoice(new UpdateDiscontinuationChoice(services));
		menu.addChoice(new UpdateCompanyChoice(services));
		menu.addChoice(new DeleteComputerChoice(services));
		menu.loop();

		conn.deconnect();
	}

}
