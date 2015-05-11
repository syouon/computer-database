package controller;

import java.util.List;

import javax.validation.Valid;

import model.Company;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.CompanyService;
import service.ComputerService;
import dto.ComputerDTO;
import dto.DTOMapper;

/**
 * Servlet implementation class EditComputerServlet
 */
@Controller
@RequestMapping(value = "/editComputer")
public class EditComputerController {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private List<Company> companies;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = "id") String idParam, ModelMap map) {
		companies = companyService.listCompanies();

		long id = 0;
		if (idParam != null && !idParam.equals("") && idParam.matches("\\d*")) {
			id = Long.parseLong(idParam);
			map.addAttribute("id", id);
		}

		Computer computer = computerService.showComputerDetails(id);
		if (computer != null) {
			ComputerDTO dto = DTOMapper.toComputerDTO(computer);
			map.addAttribute("computer", dto);
		}

		map.addAttribute("companies", companies);

		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute @Valid ComputerDTO dto,
			BindingResult result, ModelMap map) {

		/*
		 * String newName = request.getParameter("name"); String newIntroduced =
		 * request.getParameter("introduced"); String newDiscontinued =
		 * request.getParameter("discontinued"); String newCompanyId =
		 * request.getParameter("companyId"); String idParam =
		 * request.getParameter("id"); long id = Long.parseLong(idParam);
		 * 
		 * if (newName.equals("") || !Utils.isWellFormedDate(newIntroduced) ||
		 * !Utils.isWellFormedDate(newDiscontinued)) {
		 * 
		 * request.setAttribute("id", id);
		 * 
		 * Computer computer = computerService.showComputerDetails(id); if
		 * (computer != null) { ComputerDTO dto =
		 * DTOMapper.toComputerDTO(computer); request.setAttribute("computer",
		 * dto); }
		 * 
		 * request.setAttribute("companies", companies);
		 * 
		 * return "editComputer"; } else { Computer newComputer = new
		 * Computer.Builder(newName).setId(id) .build(); if
		 * (!newIntroduced.equals("")) {
		 * newComputer.setIntroductionDate(LocalDate.parse(newIntroduced)); }
		 * 
		 * if (!newDiscontinued.equals("")) {
		 * newComputer.setDiscontinuationDate(LocalDate
		 * .parse(newDiscontinued)); }
		 * 
		 * long companyId = Long.parseLong(newCompanyId); if (companyId != 0) {
		 * Company newCompany = companyService.find(companyId);
		 * newComputer.setCompany(newCompany); }
		 * 
		 * computerService.updateComputer(newComputer); return "redirect:/"; }
		 */

		if (result.hasErrors()) {
			long id = dto.getId();
			Computer computer = computerService.showComputerDetails(id);

			map.addAttribute("id", id);
			map.addAttribute("companies", companies);
			map.addAttribute("computer", DTOMapper.toComputerDTO(computer));
			return "editComputer";
		}

		Computer computer = DTOMapper.toComputer(dto);
		Company company = companyService.find(dto.getCompanyId());
		computer.setId(dto.getId());
		computer.setCompany(company);
		computerService.updateComputer(computer);
		return "redirect:/";
	}
}
