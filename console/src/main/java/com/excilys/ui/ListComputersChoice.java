package com.excilys.ui;

/**
 * The Class ListComputersChoice.
 */
public class ListComputersChoice extends Choice {

	@Override
	public void execute() {
		Pager pager = new ComputerPager();
		pager.print();
		pager.waitForAction();
	}

	@Override
	public String toString() {
		return "list computers";
	}
}
