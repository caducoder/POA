package br.com.cep.demo.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cep.demo.model.CepInfo;

@Controller
public class HomeController {
	
	private static final String VIACEP_API = "https://viacep.com.br/ws/";

	@GetMapping("/cep-json/{cep}")
	public ResponseEntity<CepInfo> cepInfoJson(@PathVariable String cep) {
		HttpClient client = HttpClient.newHttpClient();		
		
		HttpRequest request = HttpRequest.newBuilder(URI.create(VIACEP_API + cep + "/json")).header("accept", "application/json").build();
		System.out.println("Entrei no endpoint");
		var response = client.sendAsync(request, BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.join();
		System.out.println(response.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			CepInfo info = mapper.readValue(response, CepInfo.class);
			return new ResponseEntity<CepInfo>(info, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/form-cep")
	public String form() {
		return "cepForm";
	}
	
}
