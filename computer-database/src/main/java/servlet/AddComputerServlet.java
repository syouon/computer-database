package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Company> companies = CompanyService.getInstance().listCompanies();
		request.setAttribute("companies", companies);

		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/addComputer.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("company");

		if (name.equals("")) {
			throw new AddFieldException();
		}

		Computer computer = new Computer(name);

		try {
			if (!introduced.equals("")) {
				LocalDate introducedIn = LocalDate.parse(introduced);
				computer.setIntroductionDate(introducedIn);
			}

			if (!discontinued.equals("")) {
				LocalDate discontinuedIn = LocalDate.parse(discontinued);
				computer.setDiscontinuationDate(discontinuedIn);
			}
		} catch (DateTimeParseException e) {
			throw new AddFieldException();
		}

		if (companyId.matches("\\d*")) {
			long id = Long.parseLong(companyId);
			Company company = CompanyService.getInstance().find(id);
			computer.setCompany(company);
		}

		ComputerService.getInstance().addComputer(computer);
		response.addHeader("reload", "yes");
		response.sendRedirect("DashboardServlet");
	}
}
