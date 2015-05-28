package com.excilys.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class RestWebService {

	private RestTemplate template;

	public RestWebService() {
		template = new RestTemplate();
	}

	public List<Computer> listComputers(int page) {
		List<Computer> computers = new ArrayList<>();
		ComputerDTO[] dtos = template.getForObject(
				"http://localhost:8080/webservice/computers?page=" + page
						+ "&range=10", ComputerDTO[].class);

		for (ComputerDTO dto : dtos) {
			computers.add(DTOMapper.toComputer(dto));
		}

		return computers;
	}

	public List<Company> listCompanies(int page) {
		Company[] companies = template.getForObject(
				"http://localhost:8080/webservice/companies?page=" + page
						+ "&range=10", Company[].class);

		return Arrays.asList(companies);
	}
}
