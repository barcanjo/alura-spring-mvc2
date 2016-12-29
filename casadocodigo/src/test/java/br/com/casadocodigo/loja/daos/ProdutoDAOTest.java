package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import org.casadocodigo.loja.conf.JPAConfiguration;
import org.casadocodigo.loja.daos.ProdutoDAO;
import org.casadocodigo.loja.models.Produto;
import org.casadocodigo.loja.models.TipoPreco;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.config.DataSourceConfigurationTest;

/**
 * @RunWith indica qual classe irá executar os testes encontrados na suite de testes.
 * @ContextConfiguration configura quais são as classes de configuração para execução dos testes.
 * @ActiveProfiles indica qual o profile utilizado para executar os testes.
 * @author Bruno Arcanjo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDAOTest {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPorTipoLivro() {
	    List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3).buildAll();
	    List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3).buildAll();

	    livrosImpressos.stream().forEach(produtoDAO::gravar);
	    livrosEbook.stream().forEach(produtoDAO::gravar);
	    
	    BigDecimal valor = produtoDAO.somaPrecosPorTipo(TipoPreco.EBOOK);
	    Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}
}
