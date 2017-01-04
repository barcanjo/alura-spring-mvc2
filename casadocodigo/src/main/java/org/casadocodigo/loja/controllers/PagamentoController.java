package org.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.casadocodigo.loja.models.CarrinhoCompras;
import org.casadocodigo.loja.models.DadosPagamento;
import org.casadocodigo.loja.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;
	
	/**
	 * O Uso do Callable serve para indicar ao servidor que a requisição é assíncrona, e que pode
	 * liberar o recurso para outros usuários utilizarem.
	 * É utilizado o Lambda para criar o retorno de uma classe anônima para Callable
	 * @param usuario O usuário logado no sistema, captura através da anotação @AuthenticationPrincipal
	 * @param redirectAttributes Objeto com atributos enviados de uma página a outra.
	 * @return Um objeto do tipo Callable, que retornatá ao usuário uma resposta assincrônica e que não deixará outras requisições
	 * aguardando o término desta.
	 */
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes redirectAttributes) {
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";
			
			try {
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
				enviarEmailCompraProduto(usuario);
				redirectAttributes.addFlashAttribute("sucesso", response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("falha", "Valor maior que o permitido");
				return new ModelAndView("redirect:/produtos");
			}
		};
	}
	
	/**
	 * Utiliza a classe SimpleMailMessage do Spring para enviar um e-mail de confirmação de compra para o usuário.
	 * @param usuario O usuário que realizou a compra.
	 */
	private void enviarEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		email.setTo(usuario.getEmail());
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compras@casadocodigo.com.br");
		
		sender.send(email);
	}
}
