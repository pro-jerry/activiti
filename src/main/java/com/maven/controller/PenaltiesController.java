package com.maven.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maven.pojo.Penalties;
import com.maven.service.PenaltiesService;

/**
 * springMVC+mybatis测试
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/show")
public class PenaltiesController {

	@Autowired
	private PenaltiesService penaltiesService;
	
	@RequestMapping("/penalties")
	public String showPenalties(HttpServletRequest request,Model model){
		
		Penalties penalties = penaltiesService.getPenaltiesById(1);
		request.setAttribute("penalties", penalties.getAmount());
		return "penalties";
	}
}
