package com.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *首页控制器 
 */
@Controller
@RequestMapping("/main")
public class MainController {

	@RequestMapping("/index")
    public String index() {
        return "index";
    }

	@RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
