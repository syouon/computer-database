package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ComputerService;
import service.ComputerServiceImpl;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
		super();
		service = ComputerServiceImpl.getInstance();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("selection");

		if (!idParam.equals("")) {
			String[] tokens = idParam.split(",");
			for (String id : tokens) {
				service.deleteComputer(Long.parseLong(id));
			}
		}

		response.sendRedirect("DashboardServlet");
	}
}
