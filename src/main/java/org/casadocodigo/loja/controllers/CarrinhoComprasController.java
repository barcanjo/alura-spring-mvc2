package org.casadocodigo.loja.controllers;

import org.casadocodigo.loja.daos.ProdutoDAO;
import org.casadocodigo.loja.models.CarrinhoCompras;
import org.casadocodigo.loja.models.CarrinhoItem;
import org.casadocodigo.loja.models.Produto;
import org.casadocodigo.loja.models.TipoPreco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller responsável por receber as requisições com /carrinho.
 * Define também o escopo de Request para esse bean (por padrão Singleton) para que cada solicitação
 * do usuário seja tratada em uma requisição diferente.
 * Devido ao CarrinhoComprasController injetar o SessionBean CarrinhoCompras há a necessidade de defini-lo
 * com escopo de requisição.
 * @author Bruno Arcanjo
 *
 */
@Controller
@RequestMapping("/carrinho")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private CarrinhoCompras carrinho;

    @RequestMapping("/adicionar")
    public ModelAndView adicionar(Integer produtoId, TipoPreco tipoPreco) {
        ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
        CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
        carrinho.add(carrinhoItem);
        return modelAndView;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView itens() {
    	ModelAndView modelAndView = new ModelAndView("/carrinho/itens");
    	return modelAndView;
    }

    private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) {
        Produto produto = produtoDAO.find(produtoId);
        CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
        return carrinhoItem;
    }
    
    @RequestMapping("/remover")
    public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco) {
    	carrinho.remover(produtoId, tipoPreco);
    	
    	return new ModelAndView("redirect:/carrinho"); 
    }

}