package model;

import java.time.LocalDate;

/**
 * The Class Computer.
 */
public class Computer {

	/** The id. */
	private long id;

	/** The name. */
	private String name;

	/** The introduction date. */
	private LocalDate introductionDate;

	/** The discontinuation date. */
	private LocalDate discontinuationDate;

	/** The manufacturer. */
	private Company company;

	public Computer() {
	}

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
	public LocalDate getIntroductionDate() {
		return introductionDate;
	}

	/**
	 * Gets the discontinuation date.
	 *
	 * @return the discontinuation date
	 */
	public LocalDate getDiscontinuationDate() {
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

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the introduction date.
	 *
	 * @param introduced
	 *            the new introduction date
	 */
	public void setIntroductionDate(LocalDate introduced) {
		introductionDate = introduced;
	}

	/**
	 * Sets the discontinuation date.
	 *
	 * @param discontinued
	 *            the new discontinuation date
	 */
	public void setDiscontinuationDate(LocalDate discontinued) {
		discontinuationDate = discontinued;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer
	 *            the new manufacturer
	 */
	public void setCompany(Company company) {
		this.company = company;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinuationDate == null) {
			if (other.discontinuationDate != null)
				return false;
		} else if (!discontinuationDate.equals(other.discontinuationDate))
			return false;
		if (id != other.id)
			return false;
		if (introductionDate == null) {
			if (other.introductionDate != null)
				return false;
		} else if (!introductionDate.equals(other.introductionDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public class Builder {

		private Computer computer;

		public Builder() {
			computer = new Computer();
		}

		public Builder(String name) {
			computer = new Computer(name);
		}

		public Builder setId(long id) {
			computer.id = id;
			return this;
		}

		public Builder setName(String name) {
			computer.name = name;
			return this;
		}

		public Builder setIntroduced(LocalDate date) {
			computer.introductionDate = date;
			return this;
		}

		public Builder setDiscontinued(LocalDate date) {
			computer.discontinuationDate = date;
			return this;
		}

		public Builder setCompany(Company company) {
			computer.company = company;
			return this;
		}

		public Computer build() {
			return computer;
		}
	}
}
