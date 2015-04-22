package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapper.DTOMapper;
import model.Computer;
import service.ComputerService;
import service.ComputerServiceImpl;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		service = ComputerServiceImpl.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int allComputerSize = 0;

		int nbPerPage = 10;
		int currentPage = 1;
		String page = request.getParameter("page");
		String range = request.getParameter("range");
		String search = request.getParameter("search");

		if (page != null && !page.equals("") && page.matches("\\d*")) {
			currentPage = Integer.parseInt(page);
		}

		if (range != null && !range.equals("") && range.matches("\\d*")) {
			nbPerPage = Integer.parseInt(range);
		}

		List<ComputerDTO> dtos = new ArrayList<>();

		List<Computer> computers = null;

		if (search != null && !search.equals("")) {
			computers = service.search(search, (currentPage - 1) * nbPerPage,
					nbPerPage);
			allComputerSize = service.countSearchResult(search);
		} else {
			computers = service.listComputers((currentPage - 1) * nbPerPage,
					nbPerPage);
			allComputerSize = service.count();
		}
		
		// conversion des computers vers leur DTO
		for (Computer c : computers) {
			dtos.add(DTOMapper.toComputerDTO(c));
		}
		
		request.setAttribute("currentRange", nbPerPage);
		request.setAttribute("computersNumber", allComputerSize);
		request.setAttribute("pageNumber", allComputerSize / nbPerPage);
		request.setAttribute("computers", dtos);
		request.setAttribute("page", currentPage);
		request.setAttribute("search", search);
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
