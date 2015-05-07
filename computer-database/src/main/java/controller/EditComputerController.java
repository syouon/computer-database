package controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mapper.DTOMapper;
import model.Company;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.CompanyService;
import service.ComputerService;
import util.Utils;
import dto.ComputerDTO;

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
	public String doGet(HttpServletRequest request) {
		companies = companyService.listCompanies();

		long id = 0;
		String idParam = request.getParameter("id");
		if (idParam != null && !idParam.equals("") && idParam.matches("\\d*")) {
			id = Long.parseLong(idParam);
			request.setAttribute("id", id);
		}

		Computer computer = computerService.showComputerDetails(id);
		if (computer != null) {
			ComputerDTO dto = DTOMapper.toComputerDTO(computer);
			request.setAttribute("computer", dto);
		}

		request.setAttribute("companies", companies);

		return "editComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {

		String newName = request.getParameter("name");
		String newIntroduced = request.getParameter("introduced");
		String newDiscontinued = request.getParameter("discontinued");
		String newCompanyId = request.getParameter("companyId");
		String idParam = request.getParameter("id");
		long id = Long.parseLong(idParam);
		;

		if (newName.equals("") || !Utils.isWellFormedDate(newIntroduced)
				|| !Utils.isWellFormedDate(newDiscontinued)) {

			request.setAttribute("id", id);

			Computer computer = computerService.showComputerDetails(id);
			if (computer != null) {
				ComputerDTO dto = DTOMapper.toComputerDTO(computer);
				request.setAttribute("computer", dto);
			}

			request.setAttribute("companies", companies);

			return "editComputer";
		} else {
			Computer newComputer = new Computer.Builder(newName).setId(id)
					.build();
			if (!newIntroduced.equals("")) {
				newComputer.setIntroductionDate(LocalDate.parse(newIntroduced));
			}

			if (!newDiscontinued.equals("")) {
				newComputer.setDiscontinuationDate(LocalDate
						.parse(newDiscontinued));
			}

			long companyId = Long.parseLong(newCompanyId);
			if (companyId != 0) {
				Company newCompany = companyService.find(companyId);
				newComputer.setCompany(newCompany);
			}

			computerService.updateComputer(newComputer);
			return "redirect:/";
		}
	}

}
