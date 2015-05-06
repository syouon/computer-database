package servlet;

import static util.Utils.normalizeOrderBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapper.DTOMapper;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import service.CompanyService;
import service.ComputerService;
import dto.ComputerDTO;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Page page = new Page();
		String pageParam = request.getParameter("page");
		String range = request.getParameter("range");
		String search = request.getParameter("search");
		String orderBy = request.getParameter("orderby");
		String descParam = request.getParameter("desc");
		String change = request.getParameter("change");

		page.setSearch(search);
		page.setOrderBy(orderBy);

		if (descParam != null && !descParam.equals("")
				&& descParam.equals("true")) {
			page.setDesc(true);
		} else {
			page.setDesc(false);
		}

		if (change != null && !change.equals("") && change.equals("true")) {
			page.reverseDesc();
		}

		validatePage(pageParam, page);
		validateRange(range, page);
		validateSearch(search, orderBy, page);

		int allComputerSize = 0;
		if (search != null && !search.equals("")) {
			allComputerSize = service.countSearchResult(search);
		} else {
			allComputerSize = service.count();
		}

		// conversion des computers vers leur DTO
		List<ComputerDTO> dtos = new ArrayList<>();
		for (Computer c : page.getComputers()) {
			dtos.add(DTOMapper.toComputerDTO(c));
		}

		request.setAttribute("page", page);
		request.setAttribute("computersNumber", allComputerSize);
		request.setAttribute("pageNumber", allComputerSize / page.getRange());
		request.setAttribute("computers", dtos);
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp")
				.forward(request, response);
	}

	private void validatePage(String param, Page page) {
		if (param != null && !param.equals("") && param.matches("\\d*")) {
			page.setPage(Integer.parseInt(param));
		} else {
			page.setPage(1);
		}
	}

	private void validateRange(String param, Page page) {
		if (param != null && !param.equals("") && param.matches("\\d*")) {
			page.setRange(Integer.parseInt(param));
		} else {
			page.setRange(10);
		}
	}

	private void validateSearch(String searchParam, String orderByParam,
			Page page) {
		if (searchParam != null && !searchParam.equals("")) {
			if (orderByParam != null && !orderByParam.equals("")) {
				page.setComputers(service.listComputers(searchParam,
						(page.getPage() - 1) * page.getRange(),
						page.getRange(), normalizeOrderBy(orderByParam),
						page.isDesc()));
			} else {
				page.setComputers(service.listComputers(searchParam,
						(page.getPage() - 1) * page.getRange(), page.getRange()));
			}
		} else {
			if (orderByParam != null && !orderByParam.equals("")) {
				page.setComputers(service.listComputers((page.getPage() - 1)
						* page.getRange(), page.getRange(),
						normalizeOrderBy(orderByParam), page.isDesc()));
			} else {
				page.setComputers(service.listComputers((page.getPage() - 1)
						* page.getRange(), page.getRange()));
			}
		}
	}
}
