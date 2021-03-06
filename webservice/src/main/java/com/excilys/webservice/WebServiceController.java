package com.excilys.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

/** Retrieves all web services */
@RestController
public class WebServiceController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/computers", method = RequestMethod.GET)
	public List<ComputerDTO> listComputers(
			@RequestParam(value = "page") int page,
			@RequestParam(value = "range") int range) {

		if (page < 1) {
			page = 1;
		}

		if (range < 1) {
			range = 10;
		}

		List<Computer> computers = computerService.listComputers(page - 1,
				range);
		List<ComputerDTO> dtos = new ArrayList<>();

		for (Computer c : computers) {
			dtos.add(DTOMapper.toComputerDTO(c));
		}

		return dtos;
	}

	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public List<Company> listCompanies(@RequestParam(value = "page") int page,
			@RequestParam(value = "range") int range) {

		if (page < 1) {
			page = 1;
		}

		if (range < 1) {
			range = 10;
		}

		return companyService.listCompanies(page - 1, range);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createComputer(@RequestBody ComputerDTO dto) {

		Computer computer = DTOMapper.toComputer(dto);
		computerService.addComputer(computer);

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateComputer(@RequestBody ComputerDTO dto) {
		Computer computer = DTOMapper.toComputer(dto);
		computerService.updateComputer(computer);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/computer/{id}", method = RequestMethod.GET)
	public ComputerDTO find(@PathVariable(value = "id") long id) {
		Computer computer = computerService.showComputerDetails(id);
		if (computer == null) {
			return null;
		}
		return DTOMapper.toComputerDTO(computer);
	}

	@RequestMapping(value = "/deletecomputer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComputer(
			@PathVariable(value = "id") long id) {
		computerService.deleteComputer(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/deletecompany/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCompany(
			@PathVariable(value = "id") long id) {
		companyService.deleteCompany(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
