package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapper.Mapper;
import model.Computer;
import service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int allComputerNumber;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		allComputerNumber = ComputerService.getInstance().listComputers()
				.size();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String reload = request.getParameter("reload");
		if (reload != null && reload.equals("yes")) {
			System.out.println("RELOAD");
			allComputerNumber = ComputerService.getInstance().listComputers()
					.size();
		}

		int nbPerPage = 10;
		int currentPage = 1;
		String page = request.getParameter("page");
		String range = request.getParameter("range");

		if (page != null && !page.equals("") && page.matches("\\d*")) {
			currentPage = Integer.parseInt(page);
		}

		if (range != null && !range.equals("") && range.matches("\\d*")) {
			nbPerPage = Integer.parseInt(range);
		}

		List<ComputerDTO> dtos = new ArrayList<>();
		List<Computer> computers = ComputerService.getInstance().listComputers(
				(currentPage - 1) * nbPerPage, nbPerPage);
		// conversion des computers vers leur DTO
		for (Computer c : computers) {
			dtos.add(Mapper.toComputerDTO(c));
		}

		request.setAttribute("currentRange", nbPerPage);
		request.setAttribute("computersNumber", allComputerNumber);
		request.setAttribute("pageNumber", allComputerNumber / nbPerPage);
		request.setAttribute("computers", dtos);
		request.setAttribute("page", currentPage);
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
