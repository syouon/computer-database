package com.excilys.ui;

/**
 * The Class ListComputersChoice. Computer listing option in the menu.
 */
public class ListComputersChoice extends Choice {

	@Override
	public void execute() {
		Pager pager = new ComputerPager();
		pager.print();
		// wait for action like previous or next page
		pager.waitForAction();
	}

	@Override
	public String toString() {
		return "list computers";
	}
}
