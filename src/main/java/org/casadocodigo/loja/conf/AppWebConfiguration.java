package org.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.casadocodigo.loja.controllers.HomeController;
import org.casadocodigo.loja.daos.ProdutoDAO;
import org.casadocodigo.loja.models.CarrinhoCompras;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.infra.FileSaver;

@EnableWebMvc // Habilita o recurso de Web MVC do SpringMVC
@ComponentScan(basePackageClasses = {HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class}) // configurar o caminho (pacote) onde o SpringMVC irá encontrar os nossos controllers
@EnableCaching // habilita o cache do Spring
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	/**
	 * Configura o projeto para que o SpringMVC consiga encontrar as views
	 * @return Um bean da classe InternalResourceViewResolver com as configurações defindas
	 * para que o Spring possa encontrar as views 
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver () {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		/**
		 * Expõe os beans nas páginas, deixando-os visíveis e utilizáveis.
		 */
//		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
	}
	
	/**
	 * Cria um bean do Spring responsável por configurar o arquivo de mensagens que será
	 * utilizado nas páginas do projeto
	 * @return Um bean de MessageSource com as configurações das mensagens definidas 
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/message");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
	
	/**
	 * Cria um bean para registrar um DateTimeFormatter padrão para o projeto, fazendo com que
	 * não haja a necessidade usar TAGs ou recursos para formatar datas nas páginas
	 * @return Um objeto FormattingConversionService com o formato a ser utilizado nas conversões
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	/**
	 * Cria um bean do Spring MultipartResolver, que é responsável
	 * por manipular os arquivos enviados ao servidor, e permitir que um upload seja feito por exemplo.
	 * Para isso utilizamos a clase StandardServletMultipartResolver.
	 * @return Uma instância da classe StandardServletMultipartResolver
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	/**
	 * Método necessário para informar ao Spring para buscar recursos no Container caso não encontre algum.
	 * Por exemplo, se o Spring não achar arquivos .css ou .js ele vai solicitar ao Container para encontrá-los
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	
	/**
	 * Cria um objeto da classe RestTemplate que será utilizada pelo Spring
	 * para enviar dados via GET/POST
	 * @return Uma instância do bean RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * Cria uma instância da interface CacheManager para produzir um bean para gerenciar o cache no Spring.
	 * Foi utilizado o Guava, um gerenciador de cache mantido pelo Google e que possui integração com o Spring.
	 * Nele configuramos o tempo de vida do cache e o número de objetos que serão mantidos até o cache ser limpo.
	 * Para utilizar o Guava é necessário primeiro criar um CacheBuilder para definir as configurações do
	 * tempo de vida e tamanho do cache, para então passá-las ao Guava através da instância do seu objeto recém criado.
	 * @return Um bean com as configurações do cache definidas
	 */
	@Bean
	public CacheManager cacheManager() {
		CacheBuilder<Object,Object> cacheBuilder = CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterAccess(5, TimeUnit.MINUTES);
		
		GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
		guavaCacheManager.setCacheBuilder(cacheBuilder);
		
		return guavaCacheManager;
	}
	
	/**
	 * Cria uma instância da classe ViewResolver que é responsável por resolver uma requisição devolver
	 * a view adequada para a página.
	 * Foi utilizada o conceito de ContentNegotiation para gerenciar qual conteúdo é solicitado e qual o
	 * ViewResolver indicado para tratar a requisição.
	 * Dessa forma é possível que através da mesma URL, mas com extensões diferentes, o Spring retorna uma
	 * página HTML ou um objeto JSON (por exemplo).
	 * 
	 * @param manager Uma instância da classe ContentNegotiationManager injetada pelo Spring e responsável por
	 * gerenciar a negociação do conteúdo.
	 * @return Um bean da classe ViewResolver com o resolver adequado para tratar a requisição
	 */
	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager){
		// cria uma lista de ViewResolver que poderão ser escolhidos pelo ContentNegotiationManager para
		// resolver a requisição do usuário de acordo com a extensão
	    List<ViewResolver> viewResolvers = new ArrayList<>();
	    viewResolvers.add(internalResourceViewResolver());
	    viewResolvers.add(new JsonViewResolver());
	    
	    // cria uma instância da classe ContentNegotiatingViewResolver responsável por configurar os
	    // objetos ViewResovler e o ContentNegotiatingViewResolver
	    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	    resolver.setViewResolvers(viewResolvers);
	    resolver.setContentNegotiationManager(manager);
	    
	    return resolver;
	}
	
	/**
	 * Adiciona um novo interceptador que verificará a mudança de lcale do usuário
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	/**
	 * Cria um bean que armazena a configuração do locale do usuário e carrega as páginas no idioma correto.
	 * @return Uma instância de bean da interface LocalResolver com as configurações para resolver locales.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
	/**
	 * Cria um bean do tipo MailSender com todas as configurações para envio de e-mails no projeto.
	 * @return O bean devidamente configurado.
	 */
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("dev.katecafe@gmail.com");
		mailSender.setPassword("Impacta@2015");
		mailSender.setPort(587);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
}
