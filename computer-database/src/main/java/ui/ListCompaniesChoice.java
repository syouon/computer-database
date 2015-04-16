package ui;


/**
 * The Class ListCompaniesChoice.
 */
public class ListCompaniesChoice extends Choice {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
		Pager pager = new CompanyPager();
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
		return "list companies";
	}
}
