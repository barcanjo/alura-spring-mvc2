package org.casadocodigo.loja.controllers;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller que irá "monitorar" os demais controllers para que possa gerenciar as exceções lançadas por eles.
 * @author Bruno Arcanjo
 *
 */
@ControllerAdvice
public class ExceptionHandlerController {
	
	/**
	 * Trata uma exceção genérica.
	 * @param exception A exceção lançada e injetada no método pelo Spring para que ela possa ser tratada. 
	 * @return Uma página de erros para o usuário.
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView trataExceptionGenerica(Exception exception) {
		System.out.println("Erro genérico acontecendo!");
		exception.printStackTrace();
		
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}
}
