package org.casadocodigo.loja.conf;

import org.casadocodigo.loja.daos.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// habilita a segurança no Spring MVC
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	/**
	 * Configura os recursos que devem ou não ser permitidos pelo filtro do Spring Security.
	 * O recomendado é fazer os bloqueios primeiro para depois fazer as liberações
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/produtos/form").hasRole("ADMIN") // a URL /produtos/form pode ser acessada por usuários com a role ADMIN
			.antMatchers("/carrinho/**").permitAll() // a URL /carrinho pode ser acessada por todos usuários
			.antMatchers("/pagamento/**").permitAll() // a URL /pagamento pode ser acessada por todos usuários
			.antMatchers(HttpMethod.POST ,"/produtos").hasRole("ADMIN") // a URL /produtos acessada pelo método POST está disponível para usuários com a role ADMIN
			.antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN") // a URL /produtos acessada pelo método GET está disponível para usuários com a role ADMIN
			.antMatchers("/produtos/**").permitAll() // qualquer recurso contido na URL /produtos está disponível para todos usuários
			.antMatchers("/resources/**").permitAll() // libera os recursos na URL /resources/ para todos os usuários
			.antMatchers("/").permitAll() // qualquer recurso contido na URL / está disponível para todos usuários
			.anyRequest().authenticated() // qualquer outra requisição deve ser autenticada
			.and().formLogin().loginPage("/login").permitAll() // e caso não esteja autenticado é redirecionado para o formulário de login
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); // habilita o Spring Framework para receber um logout via GET na URL /logout
	}
	
	/**
	 * Configura a forma com que o Spring Security encontrará os recursos para fazer a validação de um usuário.
	 * É definido que o objeto responsável por ler um UserDetailService (ou Usuario no nesse projeto) é usuarioDAO,
	 * uma instância de UsuarioDAO, e que o encoder do password será BCryptPasswordEncoder.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDAO)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}
