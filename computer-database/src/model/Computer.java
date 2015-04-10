package model;

import java.util.Date;

public class Computer {

	private String name;
	private Date introductionDate;
	private Date discontinuationDate;
	private Company manufacturer;
	
	public Computer(String name,
			Date introduced,
			Date discontinued,
			Company manufacturer) {
		this.name = name;
		this.introductionDate = introduced;
		this.discontinuationDate = discontinued;
		this.manufacturer = manufacturer;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getIntroductionDate() {
		return introductionDate;
	}
	
	public Date getDiscontinutionDate() {
		return discontinuationDate;
	}
	
	public Company getManufacturer() {
		return manufacturer;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroductionDate(Date introduced) {
		introductionDate = introduced;
	}
	
	public void getDiscontinutionDate(Date discontinued) {
		discontinuationDate = discontinued;
	}
	
	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String toString() {
		String display = name;
		
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
