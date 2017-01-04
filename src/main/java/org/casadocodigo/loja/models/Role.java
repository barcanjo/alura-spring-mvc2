package org.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/**
 * Classe que representa as roles (ou permissões/papéis) de um usuário do sistema.
 * Para que o Spring Security reconheça essa entidade como a que representa as roles, é necessário
 * implementar a interface GrantedAuthority.
 * 
 * @author Bruno Arcanjo
 *
 */
@Entity
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String nome;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Retorna o nome da role (ou authority)
	 */
	@Override
	public String getAuthority() {
		return this.nome;
	}
}