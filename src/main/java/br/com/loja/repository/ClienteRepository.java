package br.com.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.loja.model.Cliente;

@Repository
// classe responsável por implementar o JPA para acesso ao banco de dados
public interface ClienteRepository extends JpaRepository<Cliente,String>{
	
	@Query("from Cliente where email = ?1 and senha = ?2") 
	public Cliente consultarEmailSenha(String email, String senha);
	
	public Cliente findByCpf(String cpf);

}