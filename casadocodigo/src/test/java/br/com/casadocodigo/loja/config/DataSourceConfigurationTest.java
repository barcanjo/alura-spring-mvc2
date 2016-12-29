package br.com.casadocodigo.loja.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Classe de configuração de um DataSource para ser utilizado em testes.
 * 
 * @author Bruno Arcanjo
 *
 */
public class DataSourceConfigurationTest {
	
	/**
	 * Cria um DataSource com as configurações de acesso ao banco de dados
	 * como usuário, senha, url e driver. Veja que é utilizado o Profile "test", para indicar
	 * que esse DataSource será utilizado apenas quando o Profile "test" estiver ativo
	 * @return Uma instância única de DataSource que será utilizado para a aplicação se conectar
	 * ao banco de dados.
	 * 
	 * @return
	 */
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		/**
		 * Cria um DataSource com as configurações de acesso ao banco de dados
		 * como usuário, senha, url e driver
		 */
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("postgres");
		dataSource.setPassword("G3ner@ll");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/casadocodigo_test");
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		return dataSource;
	}
}
