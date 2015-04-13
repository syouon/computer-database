package model;

import java.util.Date;

public class Computer {

	private long id;
	private String name;
	private Date introductionDate;
	private Date discontinuationDate;
	private Company manufacturer;
	
	public Computer(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getIntroductionDate() {
		return introductionDate;
	}
	
	public Date getDiscontinuationDate() {
		return discontinuationDate;
	}
	
	public Company getManufacturer() {
		return manufacturer;
	}
	
	public void setIntroductionDate(Date introduced) {
		introductionDate = introduced;
	}
	
	public void setDiscontinuationDate(Date discontinued) {
		discontinuationDate = discontinued;
	}
	
	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String toString() {
		String display = "n°" + id + ") " + name;
		
		if (introductionDate != null) {
			display += ", introduced in " + introductionDate;
		}
		
		if (discontinuationDate != null) {
			display += ", discontinued in " + discontinuationDate;
		}
		
		if (manufacturer != null) {
			display += ", by " + manufacturer;
		}
		
		return display;
	}
}
