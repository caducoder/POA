package com.geometria.analitica.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping
	String welcome() {
		return "home";
	}
	
	@GetMapping("/reta")
	String pageEquacaoReta() {
		return "reta";
	}
	
	@RequestMapping("/reta-calculos")
	public String calcularCoeficienteAngular(
			@RequestParam("xp1") double x1,
			@RequestParam("yp1") double y1,
			@RequestParam("xp2") double x2,
			@RequestParam("yp2") double y2, Model model) {
		
		String pontos = "P1("+x1 + ", "+ y1+ ") P2("+ x2+ ", "+ y2 + ")";
		double dividendo = y2 - y1;
		double divisor = x2 - x1;
		
		// verifica se a divisão é exata
		boolean isExata = dividendo % divisor == 0 ? true : false;
		
		// calcula o coeficiente angular
		double m = dividendo / divisor;
		
		// calcula o coeficiente linear
		double n = y1 - x1 * m;
		
		String coeficienteAngular = Double.toString(m);
		String coeficienteLinear = Double.toString(n);
		
		if(isExata) {
			coeficienteAngular = removeDecimal(m);
		} else {
			// arredonda para 1 casa decimal
			coeficienteAngular = arredondar(m, 1).toString();
			coeficienteLinear = arredondar(n, 1).toString();
		}
		
		// monta equação reduzida da reta
		String eqReduzida = "y = " + coeficienteAngular + "x + (" + coeficienteLinear + ")";
		
		// aplica a fórmula da distância entre dois pontos nos valores
		double dist = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
		
		// arredonda para 2 casas decimais
		dist = arredondar(dist, 2).doubleValue();
		
		// calcula ponto médio
		double pmx = (x1 + x2)/2;
		double pmy = (y1 + y2)/2;
		
		String pm = ("M(" + arredondar(pmx,1) + ", " + arredondar(pmy, 1) + ")");
		
		model.addAttribute("dist", dist);
		model.addAttribute("pontoMedio", pm);
		model.addAttribute("coa", coeficienteAngular);
		model.addAttribute("eqr", eqReduzida);
		model.addAttribute("pontos", pontos);
		return "reta";
	}
	
	private String removeDecimal(double num) {
		String ns = Double.toString(num);
		return ns.substring(0, ns.indexOf("."));
	}
	
	private BigDecimal arredondar(double num, int casas) {
		return BigDecimal.valueOf(num).setScale(casas, RoundingMode.HALF_UP);
	}
	
}
