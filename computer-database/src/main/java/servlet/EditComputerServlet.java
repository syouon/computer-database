package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDTO;
import mapper.DTOMapper;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.CompanyServiceImpl;
import service.ComputerService;
import service.ComputerServiceImpl;
import util.Utils;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private CompanyService companyService;
	private List<Company> companies;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		super();
		computerService = ComputerServiceImpl.getInstance();
		companyService = CompanyServiceImpl.getInstance();
		companies = companyService.listCompanies();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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

		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/editComputer.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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

			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/editComputer.jsp")
					.forward(request, response);
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
			response.sendRedirect("DashboardServlet");
		}
	}

}
