package org.casadocodigo.loja.models;

import java.math.BigDecimal;

/**
 * Para enviar o valor do pagamento utilizando o parâmetro value foi necessário criar uma propriedade
 * com o mesmo nome.
 * Quando o Spring fizer o post ele pegará o nome da propriedade, chamada value, que combina com o nome
 * da propriedade que o JSON espera
 * @author Bruno Arcanjo
 *
 */
public class DadosPagamento {

	private BigDecimal value;

	public DadosPagamento(BigDecimal value) {
		this.value = value;
		
	}
	
	public BigDecimal getValue() {
		return value;
	}
}
