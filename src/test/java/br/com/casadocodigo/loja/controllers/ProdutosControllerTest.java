package br.com.casadocodigo.loja.controllers;

import javax.servlet.Filter;

import org.casadocodigo.loja.conf.AppWebConfiguration;
import org.casadocodigo.loja.conf.JPAConfiguration;
import org.casadocodigo.loja.conf.SecurityConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.casadocodigo.loja.config.DataSourceConfigurationTest;

/**
 * Classe utilizada para testar o acesso aos produtos.
 * É feita uma verificação para testar o acesso à "/" e saber se ela retorna
 * a página no caminho "/WEB-INF/views/home.jsp" com o atributos "produtos".
 * Também é verificado se apenas um usuário com a role "USUARIO" não pode acessar a URL "/produtos/form". 
 * Para que os testes funcionem é necessário:
 * 1- anotar essa classe com @WebAppConfiguration para adicionar
 * o comportamento de uma aplicação Web;
 * 2- adicionar ao context de configuração a classe de configuração web do projeto AppWebConfiguration.class;
 * 3- adicionar o filtro de segurança do projeto SecurityConfiguration para que o Spring Security conheça
 * as configurações do filtro de segurança e validar as URLs e seus acessos.
 * 
 * @author Bruno Arcanjo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, DataSourceConfigurationTest.class,
		SecurityConfiguration.class})
@ActiveProfiles("test")
public class ProdutosControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	private MockMvc mockMvc;
	
	/**
	 * Altera o objeto da classe MockMvc para configurar um context de aplicação web injetando
	 * uma classe WebApplicationContext do próprio Spring, adiciona o filtro de segurança da aplicação
	 * e por fim o constrói. 
	 */
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(springSecurityFilterChain)
				.build();
	}
	
	/**
	 * Testa se a URL "/" possui um atributo chamado "produtos" e se retorna uma página no
	 * caminho "/WEB-INF/views/home.jsp"
	 * @throws Exception
	 */
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers
					.model().attributeExists("produtos"))
			.andExpect(MockMvcResultMatchers
					.forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	/**
	 * Testa se a URL "/produtos/form" retorna uma página no caminho "/WEB-INF/views/home.jsp" e se um usuário
	 * com o nome "user@casadocodigo.com.br", senha "123456" e role "USUARIO" ao tentar acessar a URL
	 * recebe o status 403. 
	 * @throws Exception
	 */
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
				.with(SecurityMockMvcRequestPostProcessors
						.user("user@casadocodigo.com.br").password("123456").roles("USUARIO")))
				.andExpect(MockMvcResultMatchers.status().is(403));
			
	}
}
