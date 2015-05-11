package controller;

import static util.Utils.normalizeOrderBy;

import java.util.ArrayList;
import java.util.List;

import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.CompanyService;
import service.ComputerService;
import dto.ComputerDTO;
import dto.DTOMapper;

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
	public String doGet(
			@RequestParam(value = "page", required = false) String pageParam,
			@RequestParam(value = "range", required = false) String range,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "orderby", required = false) String orderBy,
			@RequestParam(value = "desc", required = false) String descParam,
			@RequestParam(value = "change", required = false) String change,
			ModelMap map) {

		Page page = new Page();
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

		map.addAttribute("page", page);
		map.addAttribute("computersNumber", allComputerSize);
		map.addAttribute("pageNumber", allComputerSize / page.getRange());
		map.addAttribute("computers", dtos);

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
