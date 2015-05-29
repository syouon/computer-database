package com.excilys.ui;

/**
 * The Class ListCompaniesChoice. Company listing option in the menu.
 */
public class ListCompaniesChoice extends Choice {

	@Override
	public void execute() {
		Pager pager = new CompanyPager();
		pager.print();
		// wait for action like previous or next page
		pager.waitForAction();
	}

	@Override
	public String toString() {
		return "list companies";
	}
}
