package com.excilys.ui;

/**
 * The Class ShowDetailsChoice. Showing in details a computer option in the
 * menu.
 */
public class ShowDetailsChoice extends Choice {

	@Override
	public void execute() {
		long id = askForComputerId();
		System.out.println(webservice.find(id));
	}

	@Override
	public String toString() {
		return "show computer details";
	}
}
