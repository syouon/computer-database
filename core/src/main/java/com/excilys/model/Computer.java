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

@Entity
@Table(name = "computer")
public class Computer implements Serializable {

	private static final long serialVersionUID = -4121770083587704914L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "introduced")
	@Convert(converter = DateMapper.class)
	private LocalDate introduced;

	@Column(name = "discontinued")
	@Convert(converter = DateMapper.class)
	private LocalDate discontinued;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private Company company;

	public Computer() {
	}

	public Computer(String name) {
		this.name = name;
	}

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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

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

	/** Builder makes easier Computer construction */
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
