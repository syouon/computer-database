package controller;

import static util.Utils.normalizeOrderBy;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mapper.DTOMapper;
import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.CompanyService;
import service.ComputerService;
import dto.ComputerDTO;

/**
 * Servlet implementation class DashboardServlet
 */
@Controller
@RequestMapping(value = { "/dashboard", "/" })
public class DashboardController {
	@Autowired
	private ComputerService service;
	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) {

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

		System.out.println("PAGE COMPUTERS LENGTH:"
				+ page.getComputers().size());

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

		return "dashboard";
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
