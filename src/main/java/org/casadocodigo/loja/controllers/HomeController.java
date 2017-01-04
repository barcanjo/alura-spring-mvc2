package org.casadocodigo.loja.controllers;

import java.util.List;

import org.casadocodigo.loja.daos.ProdutoDAO;
import org.casadocodigo.loja.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe Crontroller
 * @author Bruno Arcanjo
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@RequestMapping("/")
	@Cacheable("produtosHome") // define o método como 'cacheável' e dá o nome 'produtosHome' para sua chave
	public ModelAndView index() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
}
