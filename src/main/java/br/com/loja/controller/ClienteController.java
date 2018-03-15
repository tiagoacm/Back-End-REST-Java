package br.com.loja.controller;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.loja.model.Cliente;
import br.com.loja.service.ClienteService;
import br.com.loja.validation.ValidationErrorBuilder;

@RestController
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	// consultar todos os clientes
	@RequestMapping(method = RequestMethod.GET, value = "/cliente")
	public ResponseEntity<Collection<Cliente>> consultar() throws ServletException {

		try {
			// consulta todos os clientes no banco de dados
			Collection<Cliente> clienteTodos = clienteService.consultar();

			// não encontrou nenhum cliente retorna not-found, caso contrário
			// retorna a lista de clientes encontrados no banco de dados
			if (clienteTodos.size() > 0) {
				return new ResponseEntity<>(clienteTodos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			// em caso de erro, retorna a mensagem
			throw new ServletException("Sistema indisponível, tente mais tarde.");
		}

	}

	// consultar cliente por CPF
	@RequestMapping(method = RequestMethod.GET, value = "/cliente/{cpf}")
	public ResponseEntity<Cliente> consultarCpf(@PathVariable String cpf) throws ServletException {

		try {
			// verifica se cliente existe no banco de dados
			Cliente clienteEncontrado = clienteService.consultarCpf(cpf);

			// cliente não encontrado retorna not_found, caso contrário retorna
			// os dados encontrado no banco de dados
			if (clienteEncontrado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(clienteEncontrado, HttpStatus.OK);
			}

		} catch (Exception e) {
			// em caso de erro, retorna a mensagem			
			throw new ServletException("Sistema indisponível, tente mais tarde.");
		}

	}

	// incluir cliente
	@RequestMapping(method = RequestMethod.POST, value = "/cliente", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> incluir(@Valid @RequestBody Cliente cliente, BindingResult bindingResult)
			throws ServletException {

		// verifica se existe alguma inconsistência nos dados de entrada
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(bindingResult));
		}

		// verifica se CPF já está cadastrado no banco de dados
		Cliente clienteEncontrado = clienteService.consultarCpf(cliente.getCpf());

		if (clienteEncontrado == null) {
			try {
				// se cliente não encontrado, executa a inclusão do cliente no
				// banco de dados
				Cliente clienteIncluido = clienteService.incluir(cliente);
				// retorna cliente e resposta de sucesso
				return new ResponseEntity<>(clienteIncluido, HttpStatus.CREATED);
			} catch (Exception e) {
				// em caso de erro, retorna a mensagem
				throw new ServletException("Sistema indisponível, tente mais tarde.");
			}
		} else {
			// cpf encontrado na banco de dados, retorna mensagem
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// alterar cliente
	@RequestMapping(method = RequestMethod.PUT, value = "/cliente", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> alterar(@Valid @RequestBody Cliente cliente, BindingResult bindingResult)
			throws ServletException {

		// verifica se existe alguma inconsistência nos dados de entrada
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(bindingResult));
		}

		try {
			// verifica se cliente existe na base de dados
			Cliente clienteEncontrado = clienteService.consultarCpf(cliente.getCpf());

			// cliente nao encontrado retorna not_found, caso contrário executa
			// alteração no banco de dados e retorna ok
			if (clienteEncontrado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				Cliente clienteAlterado = clienteService.alterar(cliente);
				return new ResponseEntity<>(clienteAlterado, HttpStatus.CREATED);
			}

		} catch (Exception e) {
			// em caso de erro, retorna a mensagem
			throw new ServletException("Sistema indisponível, tente mais tarde.");
		}

	}

	// excluir cliente
	@RequestMapping(method = RequestMethod.DELETE, value = "/cliente/{cpf}")
	public ResponseEntity<Cliente> excluir(@PathVariable String cpf) throws ServletException {

		try {
			// verifica se cliente existe no banco de dados
			Cliente clienteEncontrado = clienteService.consultarCpf(cpf);

			// cliente não encontrado retorna not_found, caso contrário executa
			// exclusão no banco de dados e retorna ok
			if (clienteEncontrado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				clienteService.excluir(clienteEncontrado);
				return new ResponseEntity<>(HttpStatus.OK);
			}

		} catch (Exception e) {
			// em caso de erro, retorna a mensagem
			throw new ServletException("Sistema indisponível, tente mais tarde.");
		}

	}

}
