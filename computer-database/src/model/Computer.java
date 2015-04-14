package model;

import java.time.LocalDateTime;

/**
 * The Class Computer.
 */
public class Computer {

	/** The id. */
	private long id;

	/** The name. */
	private String name;

	/** The introduction date. */
	private LocalDateTime introductionDate;

	/** The discontinuation date. */
	private LocalDateTime discontinuationDate;

	/** The manufacturer. */
	private Company company;

	/**
	 * Instantiates a new computer.
	 *
	 * @param name
	 *            the name
	 */
	public Computer(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new computer.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 */
	public Computer(long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the introduction date.
	 *
	 * @return the introduction date
	 */
	public LocalDateTime getIntroductionDate() {
		return introductionDate;
	}

	/**
	 * Gets the discontinuation date.
	 *
	 * @return the discontinuation date
	 */
	public LocalDateTime getDiscontinuationDate() {
		return discontinuationDate;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the introduction date.
	 *
	 * @param introduced
	 *            the new introduction date
	 */
	public void setIntroductionDate(LocalDateTime introduced) {
		introductionDate = introduced;
	}

	/**
	 * Sets the discontinuation date.
	 *
	 * @param discontinued
	 *            the new discontinuation date
	 */
	public void setDiscontinuationDate(LocalDateTime discontinued) {
		discontinuationDate = discontinued;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer
	 *            the new manufacturer
	 */
	public void setManufacturer(Company manufacturer) {
		this.company = manufacturer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String display = "n°" + id + ") " + name;

		if (introductionDate != null) {
			display += ", introduced in " + introductionDate;
		}

		if (discontinuationDate != null) {
			display += ", discontinued in " + discontinuationDate;
		}

		if (company != null) {
			display += ", by " + company;
		}

		return display;
	}
}
