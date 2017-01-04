package org.casadocodigo.loja.conf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Implementa o comportamento para resolver conteúdos do tipo application/json na aplicação
 * que foram gerenciados pelo ContentNegotiationManager
 * 
 * @author Bruno Arcanjo
 *
 */
public class JsonViewResolver implements ViewResolver {
	
	/**
	 * Cria uma View JSON utilizando MappingJackson2JsonView e configura a exibição de forma elegante
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrettyPrint(true);
        return jsonView;
	}

}
