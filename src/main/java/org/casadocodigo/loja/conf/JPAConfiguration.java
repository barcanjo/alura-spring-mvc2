package org.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Classe de configuração do JPA para indicar os dados de conexão com o banco de dados, transações, mapeamentos
 * e implementação do Hibernate
 * @author Bruno Arcanjo
 *
 */

// Habilita o gerenciamento de tranações
@EnableTransactionManagement
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			DataSource dataSource, Properties aditionalProperties) {
		/**
		 * cria um LocalContainerEntityManagerFactoryBean para retornar ao Spring
		 * para a criação de um EntityManagerFactory
		 */
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		/**
		 * Cria um JpaVendorAdapter para informar que será utilizada a implementação do Hibernate sobre o JPA
		 */
		JpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		
		factoryBean.setDataSource(dataSource);
		
		factoryBean.setJpaProperties(aditionalProperties);
		
		/**
		 * Indica o pacote onde estão as classes anotadas com @Entity, para que possam
		 * ser gerenciadas pelo JPA
		 */
		factoryBean.setPackagesToScan("org.casadocodigo.loja.models");
		
		return factoryBean;
	}

	/**
	 * Configura as propriedades do Hibernate.
	 * @return Um bnea da classe Properties configurado para o Hibernate e utilizado apenas no Profile "dev". 
	 */
	@Bean
	@Profile("dev")
	public Properties aditionalProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}
	
	/**
	 * Cria um DataSource com as configurações de acesso ao banco de dados
	 * como usuário, senha, url e driver. Veja que é utilizado o Profile "dev", para indicar
	 * que esse DataSource será utilizado apenas quando o Profile "dev" estiver ativo.
	 * 
	 * @return Uma instância única de DataSource que será utilizado para a aplicação se conectar
	 * ao banco de dados.
	 */
	@Bean
	@Profile("dev")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("postgres");
		dataSource.setPassword("G3ner@ll");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/casadocodigo");
		dataSource.setDriverClassName("org.postgresql.Driver");
		return dataSource;
	}
	
	/**
	 * O bean que será o gerenciador das transações, isto é, a partir desse bean o Spring fornecerá as transações para o EntityManager
	 * @param emf O EntityManagerFactory injetado pelo Spring e utilizado para receber as transções e repassá-las aos EntityManager's
	 * @return Um JpaTransactionManager com o atual EntityManagerFactory configurado
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
}