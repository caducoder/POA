package br.com.cep.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CepController {
	
	@GetMapping("/")
	public String sayHello() {
		return "Bem vindo!";
	}
}
