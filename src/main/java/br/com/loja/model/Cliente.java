package br.com.loja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Cliente extends Usuario {

	@NotNull(message = "Endereço deve ser informado")
	@Size(max = 100, message = "Endereço deve ter no máximo {max} caracteres")
	@Column(name = "endereco", nullable = false, length = 100)
	private String endereco;

	@NotNull(message = "Municipio deve ser informado")
	@Size(max = 50, message = "Municipio deve ter no máximo {max} caracteres")
	@Column(name = "municipio", nullable = false, length = 50)
	private String municipio;

	@NotNull(message = "Estado deve ser informado")
	@Size(max = 02, message = "Estado deve ter no máximo {max} caracteres")
	@Column(name = "estado", nullable = false, length = 02)
	private String estado;

	@NotNull(message = "Telefone deve ser informado")
	@Size(max = 11, message = "Telefone deve ter no máximo {max} caracteres")
	@Column(name = "telefone", nullable = false, length = 11)
	private String telefone;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
