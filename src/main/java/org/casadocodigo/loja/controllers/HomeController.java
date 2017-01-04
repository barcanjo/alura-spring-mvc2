package org.casadocodigo.loja.controllers;

import java.util.Arrays;
import java.util.List;

import org.casadocodigo.loja.daos.ProdutoDAO;
import org.casadocodigo.loja.daos.UsuarioDAO;
import org.casadocodigo.loja.models.Produto;
import org.casadocodigo.loja.models.Role;
import org.casadocodigo.loja.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@RequestMapping("/")
	@Cacheable("produtosHome") // define o método como 'cacheável' e dá o nome 'produtosHome' para sua chave
	public ModelAndView index() {
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/criar-usuario-admin")
	public String criarUsuarioAdmin() {
		Usuario usuario = new Usuario();
		usuario.setNome("Admin");
		usuario.setEmail("admin@casadocodigo.com.br");
		usuario.setSenha("$2a$06$iZq02sL9u4Re3yvRoj2.feRWjyFxSM0rbJPaBe9XRxgdWZ2pgck4G");
		usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		
		usuarioDAO.gravar(usuario);
		
		return "Usuário admin criado com sucesso!";
	}
}
