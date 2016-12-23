package org.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
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
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
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
		
		/**
		 * Cria um DataSource com as configurações de acesso ao banco de dados
		 * como usuário, senha, url e driver
		 */
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("postgres");
		dataSource.setPassword("G3ner@ll");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/casadocodigo");
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		factoryBean.setDataSource(dataSource);
		
		/**
		 * Configura as propriedades do Hibernate
		 */
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		factoryBean.setJpaProperties(props);
		
		/**
		 * Indica o pacote onde estão as classes anotadas com @Entity, para que possam
		 * ser gerenciadas pelo JPA
		 */
		factoryBean.setPackagesToScan("org.casadocodigo.loja.models");
		
		return factoryBean;
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