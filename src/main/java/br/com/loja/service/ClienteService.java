package br.com.loja.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.loja.model.Cliente;
import br.com.loja.repository.ClienteRepository;

@Service
// classe responsavel por chamar os métodos do banco de dados para persistência
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	// CONSULTAR TODOS OS CLIENTES
	public Collection<Cliente> consultar() {
		return clienteRepository.findAll();
	}

	//CONSULTAR CLIENTE POR ID
	public Cliente consultarCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	//INCLUIR CLIENTE
	public Cliente incluir(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	//ALTERAR CLIENTE
	public Cliente alterar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	//EXCLUIR CLIENTE
	public void excluir(Cliente cliente) {
		clienteRepository.delete(cliente);
	}

	//BUSCAR POR EMAIL PARA SER UTILIZAR NO ACESSOCONTROLLER
	public Cliente consultarEmailSenha(String email, String senha) {
		return clienteRepository.consultarEmailSenha(email, senha);
	}

}
