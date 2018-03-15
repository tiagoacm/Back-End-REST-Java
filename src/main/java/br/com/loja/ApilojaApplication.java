package br.com.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.com.loja.filter.TokenFilter;

@SpringBootApplication
public class ApilojaApplication {

	// registra o filtro na aplicação para verificar toda requisição.
	// toda requisição que tiver a palavra "security" deve ter token válido
	@Bean
	public FilterRegistrationBean filtroJwt() {
		FilterRegistrationBean filtroApp = new FilterRegistrationBean();
		filtroApp.setFilter(new TokenFilter());
		filtroApp.addUrlPatterns("/security/*");
		return filtroApp;

	}

	public static void main(String[] args) {
		SpringApplication.run(ApilojaApplication.class, args);
	}
}
