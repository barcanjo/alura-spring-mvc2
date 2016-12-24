package org.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe que representa um usuário autenticado que acessará o sistema.
 * Para que o Spring Security reconheça essa entidade como a que representa um usuário, é necessário
 * implementar a interface UserDetails.
 *  
 * @author Bruno Arcanjo
 *
 */
@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String nome;

	private String senha;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * Retorna as permissões do usuário
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}
	
	/**
	 * Retorna a senha do usuário
	 */
	@Override
	public String getPassword() {
		return this.senha;
	}
	
	/**
	 * Retorna o nome do usuário, que nesse projeto é o e-mail
	 */
	@Override
	public String getUsername() {
		return this.email;
	}
	
	/**
	 * Retorna um true padrão informando que a conta não está expirada
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * Retorna um true padrão informando que a conta não está bloqueada
	 */

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * Retorna um true informando que as credenciais não estão expirada
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * Retorna um true informando que a conta está habilitada
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
