package org.casadocodigo.loja.conf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Classe responsável por resolver conteúdos do tipo application/json que devem ser gerenciados
 * pelo ContentNegotiationManager.
 * 
 * @author Bruno Arcanjo
 *
 */
public class JsonViewResolver implements ViewResolver {
	
	/**
	 * Cria um resolver para JSON que impre o JSON de forma elegante
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		
		return jsonView;
	}

}
