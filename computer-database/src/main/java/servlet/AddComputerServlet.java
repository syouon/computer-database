package servlet;

import java.io.IOException;
import java.time.LocalDate;
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
	
	private List<Company> companies;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		companies = CompanyService.getInstance().listCompanies();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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

		Computer computer = new Computer(name);

		if (!introduced.equals("") && isWellFormedDate(introduced)) {
			LocalDate introducedIn = LocalDate.parse(introduced);
			computer.setIntroductionDate(introducedIn);
		}

		if (!discontinued.equals("") && isWellFormedDate(discontinued)) {
			LocalDate discontinuedIn = LocalDate.parse(discontinued);
			computer.setDiscontinuationDate(discontinuedIn);
		}

		if (companyId.matches("\\d*")) {
			long id = Long.parseLong(companyId);
			Company company = CompanyService.getInstance().find(id);
			computer.setCompany(company);
		}

		if (!name.equals("") && (introduced.equals("") || isWellFormedDate(introduced))
				&& (discontinued.equals("") || isWellFormedDate(discontinued))) {
			
			ComputerService.getInstance().addComputer(computer);
			response.sendRedirect("DashboardServlet?reload=yes");
			
		} else {
			request.setAttribute("companies", companies);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/addComputer.jsp")
					.forward(request, response);
		}
	}

	private boolean isWellFormedDate(String time) {
		if (!time.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return false;
		}

		String[] tokens = time.split("-");
		//int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);

		if (month > 12 || day > 31) {
			return false;
		}

		return true;
	}
}
