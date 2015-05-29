package com.excilys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.service.ComputerService;

/** DeleteComputerController is called when url "/deleteComputer" is requested */
@Controller
@RequestMapping(value = "/deleteComputer")
public class DeleteComputerController {
	@Autowired
	private ComputerService service;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(
			@RequestParam(value = "selection", required = false) String idParam) {

		if (!idParam.equals("")) {
			String[] tokens = idParam.split(",");
			for (String id : tokens) {
				service.deleteComputer(Long.parseLong(id));
			}
		}

		return "redirect:/";
	}
}
