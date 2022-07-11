package com.api.sigabem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@RestController
public class SigaBemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigaBemApplication.class, args);
		System.out.println("Teste");
	}

	@GetMapping("/")
	public String index() {
		return "Olá Mundo";
	}

}
