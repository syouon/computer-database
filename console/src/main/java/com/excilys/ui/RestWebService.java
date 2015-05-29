package com.excilys.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * Singleton RestWebService. Provides some webservices to manipulate the
 * computer database.
 */
public enum RestWebService {
	INSTANCE;

	// Spring object for communication with webservices
	private RestTemplate template;

	// Root url of webservices
	private static final String URL = "http://localhost:8080/webservice/";

	private RestWebService() {
		template = new RestTemplate();
	}

	/**
	 * List computers.
	 *
	 * @param page
	 *            the page's number
	 * @return the list of computer for the page
	 */
	public List<Computer> listComputers(int page) {
		List<Computer> computers = new ArrayList<>();
		ComputerDTO[] dtos = template.getForObject(URL + "computers?page="
				+ page + "&range=10", ComputerDTO[].class);

		for (ComputerDTO dto : dtos) {
			computers.add(DTOMapper.toComputer(dto));
		}

		return computers;
	}

	/**
	 * List companies.
	 *
	 * @param page
	 *            the page's number
	 * @return the list of company for the page
	 */
	public List<Company> listCompanies(int page) {
		Company[] companies = template.getForObject(URL + "companies?page="
				+ page + "&range=10", Company[].class);

		return Arrays.asList(companies);
	}

	/**
	 * Find a computer.
	 *
	 * @param id
	 *            the computer's id
	 * @return the computer if found, null if not
	 */
	public Computer find(long id) {
		ComputerDTO dto = template.getForObject(URL + "computer/" + id,
				ComputerDTO.class);
		if (dto == null) {
			return null;
		}
		return DTOMapper.toComputer(dto);
	}

	/**
	 * Adds a computer.
	 *
	 * @param computer
	 *            the computer to add
	 */
	public void addComputer(Computer computer) {
		template.postForObject(URL + "create",
				DTOMapper.toComputerDTO(computer), ComputerDTO.class);
	}

	/**
	 * Update a computer.
	 *
	 * @param computer
	 *            the computer with new fields. Should also provide the
	 *            computer's id to update
	 */
	public void updateComputer(Computer computer) {
		template.put(URL + "update", DTOMapper.toComputerDTO(computer));
	}

	/**
	 * Delete a computer.
	 *
	 * @param id
	 *            the computer's id
	 */
	public void deleteComputer(long id) {
		template.delete(URL + "deletecomputer/" + id);
	}

	/**
	 * Delete a company.
	 *
	 * @param id
	 *            the company's id
	 */
	public void deleteCompany(long id) {
		template.delete(URL + "deletecompany/" + id);
	}
}