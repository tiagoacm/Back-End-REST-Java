package br.com.loja.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja.model.Cliente;
import br.com.loja.service.ClienteService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AcessoController {

	@Autowired
	private ClienteService clienteService;

	// método para autenticar cliente verificando banco de dados e gerando token de acesso
	@RequestMapping(value = "/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> autenticar(@RequestBody Cliente cliente) throws ServletException {

		// verifica se email e senha foram informados
		if (cliente.getEmail() == null || cliente.getSenha() == null) {
			return new ResponseEntity<>("Email e senha obrigatório" , HttpStatus.BAD_REQUEST);
		}

		// Verifica se email e senha estão no banco de dados
		Cliente clienteAutenticado = clienteService.consultarEmailSenha(cliente.getEmail(), cliente.getSenha());

		// cliente não encontrado
		if (clienteAutenticado == null) {
			return new ResponseEntity<>("Email ou senha inválidos" , HttpStatus.BAD_REQUEST);
		}

		// Cliente com email e senha válida, será gerado um Token de acesso com expiração em 60 minutos
		// tempo de alteração pode ser alterado.
		String token = Jwts.builder()
				.setSubject(clienteAutenticado.getCpf())
				.signWith(SignatureAlgorithm.HS512, "chave338")
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).compact();

		//return new AcessoResponse(token, clienteAutenticado.getCpf(),clienteAutenticado.getNome());
		AcessoResponse acesso = new AcessoResponse(token, clienteAutenticado.getCpf(),clienteAutenticado.getNome());
		return new ResponseEntity<>(acesso, HttpStatus.OK);

	}

	// classe privada apenas para retornar o token, classe não será persistida
	private class AcessoResponse {
		public String token;
		public String cpfCliente;
		public String nomeCliente;

		public AcessoResponse(String token, String cpfCliente, String nomeCliente) {
			this.token = token;
			this.cpfCliente = cpfCliente;
			this.nomeCliente = nomeCliente;
		}

	}
}