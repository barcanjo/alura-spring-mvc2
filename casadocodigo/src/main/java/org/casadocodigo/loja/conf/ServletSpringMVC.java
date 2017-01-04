package org.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/**
	 * Adiciona configurações que serão carregadas assim que a aplicação for iniciada
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class};
	}

	/**
	 * A classe AppWebConfiguration será usada como classe de configuração do servlet do SpringMVC
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{};
	}
	
	/**
	 *  Define que o servlet do SpringMVC atenderá as requisições a partir da raiz do nosso projeto (/)
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
	/**
	 * Retorna filtros do Servlet com a configuração do enconding em UTF-8 e adiciona o filtro
	 * OpenEntityManagerInViewFilter para que a transação do Hibernate seja finalizada apenas quando
	 * a página for carregada. 
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		
		return new Filter[]{characterEncodingFilter, new OpenEntityManagerInViewFilter()};
	}
	
	/**
	 * Sobrescreve o método customizeRegistration que configura um Multipart.
	 * É passado um objeto MultpartConfigElement com as configurações padrões.
	 * Isso é necessário para poder trabalhar com os objetos da classe MultipartFile
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	/**
	 * Método executado quando a aplicação é iniciada.
	 * Nele definimos um listener verificará algumas mudanças no projeto, e nele configuramos
	 * um parâmetro que ativa o Profile "dev" para a aplicação.
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);
		servletContext.setInitParameter("spring.profiles.active", "dev");
	}
}