package com.excilys.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.excilys.util.LocalDateFormat;

/*
 * Data Transfer Object of a computer. Used to send Computer object to the server. 
 */
public class ComputerDTO {

	@Min(0)
	private long id;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@LocalDateFormat
	private String introduced;

	@NotNull
	@LocalDateFormat
	private String discontinued;

	@Min(0)
	private long companyId;

	private String companyName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + ", companyName=" + companyName
				+ "]";
	}
}
