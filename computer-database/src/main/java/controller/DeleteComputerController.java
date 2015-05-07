package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
@RequestMapping(value = "/deleteComputer")
public class DeleteComputerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService service;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(HttpServletRequest request)
			throws ServletException, IOException {
		String idParam = request.getParameter("selection");

		if (!idParam.equals("")) {
			String[] tokens = idParam.split(",");
			for (String id : tokens) {
				service.deleteComputer(Long.parseLong(id));
			}
		}

		return "redirect:/";
	}
}
