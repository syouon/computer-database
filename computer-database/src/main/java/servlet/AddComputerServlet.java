package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import service.CompanyService;
import service.ComputerService;
import util.Utils;

/**
 * Servlet implementation class AddComputerServlet
 */
@Controller
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	private List<Company> companies;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		companies = companyService.listCompanies();
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
			response.sendRedirect("DashboardServlet");

		} else {
			request.setAttribute("companies", companies);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/addComputer.jsp")
					.forward(request, response);
		}
	}
}
