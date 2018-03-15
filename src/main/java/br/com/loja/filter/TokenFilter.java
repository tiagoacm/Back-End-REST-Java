package br.com.loja.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

// classe responsável por filtrar as requisições e verificar se o token é válido
public class TokenFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		// pega informação do header da chamada
		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou inválido");
		}
		// extraindo somente a string com o token sem a palavra "Bearer "
		String token = header.substring(7);

		try {
			// pega token no header e criptografa com a chave para verificar se é válido
			Jwts.parser().setSigningKey("chave338").parseClaimsJws(token).getBody();
		} catch (Exception e) {
			// devolve status 401 no response em caso de erro
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Inválido");
		}

		// Se não aconteceu nenhuma exception, processamento continua normal
		chain.doFilter(request, response);

	}
}
