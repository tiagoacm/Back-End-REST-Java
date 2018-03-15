package br.com.loja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {

	@Id
	@CPF(message = "CPF inválido")
	@Column(name = "cpf", length = 11)
	private String cpf;

	@NotNull(message = "Nome deve ser informado")
	@Size(max = 70, message = "Nome deve ter no máximo {max} caracteres")
	@Column(name = "nome", nullable = false, length = 70)
	private String nome;

	@NotNull(message = "Email deve ser informado")
	@Size(max = 100, message = "Email deve ter no máximo {max} caracteres")
	@Email(message = "Email no formato inválido")
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@NotNull(message = "Senha deve ser informada")
	@Size(min = 6, max = 10, message = "Senha deve ser entre {min} e {max} caracteres")
	@Column(name = "senha", nullable = false, length = 10)
	private String senha;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
