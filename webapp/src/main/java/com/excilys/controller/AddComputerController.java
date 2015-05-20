package com.excilys.controller;

import java.util.List;

import javax.validation.Valid;

import com.excilys.model.Company;
import com.excilys.model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.dto.ComputerDTO;
import com.excilys.dto.DTOMapper;

@Controller
@RequestMapping(value = "/addcomputer")
public class AddComputerController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	private List<Company> companies;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap map) {
		companies = companyService.listCompanies();
		map.addAttribute("companies", companies);
		map.addAttribute("computerDTO", new ComputerDTO());

		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute @Valid ComputerDTO dto,
			BindingResult result, ModelMap map) {

		if (result.hasErrors()) {
			map.addAttribute("companies", companies);
			return "addComputer";
		}

		long companyId = dto.getCompanyId();
		Company company = companyService.find(companyId);
		if (company != null) {
			dto.setCompanyName(company.getName());
		}

		Computer c = DTOMapper.toComputer(dto);
		System.out.println(c);
		computerService.addComputer(c);
		return "redirect:/";
	}
}
