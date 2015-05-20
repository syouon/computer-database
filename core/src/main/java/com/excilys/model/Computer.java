package com.excilys.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.excilys.mapper.DateMapper;

/**
 * The Class Computer.
 */
@Entity
@Table(name = "computer")
public class Computer implements Serializable {

	private static final long serialVersionUID = -4121770083587704914L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	/** The name. */
	@Column(name = "name")
	private String name;

	/** The introduction date. */
	@Column(name = "introduced")
	@Convert(converter = DateMapper.class)
	private LocalDate introduced;

	/** The discontinuation date. */
	@Column(name = "discontinued")
	@Convert(converter = DateMapper.class)
	private LocalDate discontinued;

	/** The manufacturer. */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
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
	public LocalDate getIntroduced() {
		return introduced;
	}

	/**
	 * Gets the discontinuation date.
	 *
	 * @return the discontinuation date
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
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

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the introduction date.
	 *
	 * @param introduced
	 *            the new introduction date
	 */
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	/**
	 * Sets the discontinuation date.
	 *
	 * @param discontinued
	 *            the new discontinuation date
	 */
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
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

		if (introduced != null) {
			display += ", introduced in " + introduced;
		}

		if (discontinued != null) {
			display += ", discontinued in " + discontinued;
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
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static class Builder {

		private Computer computer;

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
			computer.introduced = date;
			return this;
		}

		public Builder setDiscontinued(LocalDate date) {
			computer.discontinued = date;
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
