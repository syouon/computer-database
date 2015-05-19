package ui;


/**
 * The Class DeleteComputerChoice.
 */
public class DeleteComputerChoice extends Choice {

	@Override
	public void execute() {
		long id = askForComputerId();
		computerService.deleteComputer(id);
	}

	@Override
	public String toString() {
		return "delete a computer";
	}
}
