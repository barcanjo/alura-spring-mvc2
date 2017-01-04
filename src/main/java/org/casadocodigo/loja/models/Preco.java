package org.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

/**
 * Classe preco que faz parte de uma outra classe, nesse caso de Produto. Isso é definido com a anotação @Embeddable
 * @author Bruno Arcanjo
 *
 */
@Embeddable
public class Preco {

	private BigDecimal valor;
	private TipoPreco tipo;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoPreco getTipo() {
		return tipo;
	}

	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Preco [valor=" + valor + ", tipo=" + tipo + "]";
	}
}