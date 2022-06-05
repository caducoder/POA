package com.geometria.analitica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping
	String welcome(Model model) {
		model.addAttribute("somth", "this is from the controller.");
		return "home";
	}
	
	@GetMapping("/distancia-ponto")
	String distanciaPonto() {
		return "option1";
	}
}
