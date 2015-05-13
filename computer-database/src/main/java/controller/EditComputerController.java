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
		map.addAttribute("computerDTO", new ComputerDTO());

		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute @Valid ComputerDTO dto,
			BindingResult result, ModelMap map) {

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
