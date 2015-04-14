package main;

import services.CompanyService;
import services.ComputerService;
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

public class Main {

	public static void main(String[] args) {

		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance()
		
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
	}

}
