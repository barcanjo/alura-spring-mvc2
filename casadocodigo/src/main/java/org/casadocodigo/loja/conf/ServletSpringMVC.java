package org.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

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
	 * Retorna filtros do Servlet com a configuração do enconding em UTF-8
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		
		return new Filter[]{characterEncodingFilter};
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
}