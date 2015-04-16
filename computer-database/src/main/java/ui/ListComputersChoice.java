package ui;

/**
 * The Class ListComputersChoice.
 */
public class ListComputersChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		Pager pager = new ComputerPager();
		pager.print();
		pager.waitForAction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "list computers";
	}
}
