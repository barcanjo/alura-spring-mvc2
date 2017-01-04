package org.casadocodigo.loja.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Classe de configuração do JPA para conexão com o banco de dados da aplicação, disponível apenas para o profile "prod".
 * 
 * @author Bruno Arcanjo
 *
 */
@Profile("prod")
public class JPAProductionConfiguration {
	
	@Autowired
	private Environment enviroment;

	/**
	 * Configura as propriedades do Hibernate.
	 * @return Um bnea da classe Properties configurado para o Hibernate e utilizado apenas no Profile "prod". 
	 */
	@Bean
	public Properties aditionalProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}
	
	/**
	 * Cria um DataSource com as configurações de acesso ao banco de dados
	 * como usuário, senha, url e driver. Veja que é utilizado o Profile "prod", para indicar
	 * que esse DataSource será utilizado apenas quando o Profile "dev" estiver ativo.
	 * 
	 * @return Uma instância única de DataSource que será utilizado para a aplicação se conectar
	 * ao banco de dados.
	 * @throws URISyntaxException Erro na configuração do URI.
	 */
	@Bean
	public DataSource dataSource() throws URISyntaxException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		URI dbURI = new URI(enviroment.getProperty("DATABASE_URL"));
		dataSource.setUrl("jdbc:postgresql://" + dbURI.getHost() + ":" + dbURI.getPort() + dbURI.getPath());
		dataSource.setUsername(dbURI.getUserInfo().split(":")[0]);
		dataSource.setPassword(dbURI.getUserInfo().split(":")[1]);
		dataSource.setDriverClassName("org.postgresql.Driver");
		return dataSource;
	}
}
