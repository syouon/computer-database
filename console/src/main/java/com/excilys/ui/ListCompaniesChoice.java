package com.excilys.ui;

/**
 * The Class ListCompaniesChoice.
 */
public class ListCompaniesChoice extends Choice {

	@Override
	public void execute() {
		Pager pager = new CompanyPager();
		pager.print();
		pager.waitForAction();
	}

	@Override
	public String toString() {
		return "list companies";
	}
}
