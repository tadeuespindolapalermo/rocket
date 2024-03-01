package br.com.redemobconsorcio.rocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping({"**/index"})
	public String index() {
		return "index";
	}

	@GetMapping("/login")
    public String login() {
        return "publico/login";
    }
}
