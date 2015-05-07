package controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Company;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.CompanyService;
import service.ComputerService;
import util.Utils;

@Controller
@RequestMapping(value = "/addcomputer")
public class AddComputerController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	private List<Company> companies;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {
		companies = companyService.listCompanies();
		request.setAttribute("companies", companies);

		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {

		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");

		Computer computer = new Computer.Builder(name).build();

		if (introduced != null && !introduced.equals("")
				&& Utils.isWellFormedDate(introduced)) {
			LocalDate introducedIn = LocalDate.parse(introduced);
			computer.setIntroductionDate(introducedIn);
		}

		if (discontinued != null && !discontinued.equals("")
				&& Utils.isWellFormedDate(discontinued)) {
			LocalDate discontinuedIn = LocalDate.parse(discontinued);
			computer.setDiscontinuationDate(discontinuedIn);
		}

		if (companyId.matches("\\d*")) {
			long id = Long.parseLong(companyId);

			if (id != 0) {
				Company company = companyService.find(id);
				computer.setCompany(company);
			}
		}

		if (name != null
				&& !name.equals("")
				&& (introduced.equals("") || Utils.isWellFormedDate(introduced))
				&& (discontinued.equals("") || Utils
						.isWellFormedDate(discontinued))) {

			computerService.addComputer(computer);
			return "redirect:/";

		} else {
			request.setAttribute("companies", companies);
			return "addComputer";
		}
	}
}
