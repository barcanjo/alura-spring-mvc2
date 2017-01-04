package org.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Classe que representa o carrinho de compras da loja
 * Define também o escopo de Sessão para esse bean (por padrão Singleton) para que não seja compartilhado
 * entre múltiplos usuários, fazendo com que seus itens sejam os mesmos.
 * Cria um proxy para resolver a dependência entre os escopos dos beans que utilizam esse Bean, fazendo com
 * que sejam do tipo Request
 * A cada sessão um novo CarrinhoCompras é criado para o usuário.
 * @author Bruno Arcanjo
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
    
    /**
	 * Adiciona um item ao carrinho de compras e adicionando a quantidade desse item no carrinho
	 * @param item O item que será adicionado ao carrinho
	 */
    public void add(CarrinhoItem item) {
        itens.put(item, getQuantidade(item) + 1);
    }
    
    /**
	 * Retorna a quantidade do item no carrinho de compras
	 * @param item O item que se quer obter a quantidade existente no carrinho de compras
	 * @return A quantidade do item
	 */
    public Integer getQuantidade(CarrinhoItem item) {
        if (!itens.containsKey(item)) {
            itens.put(item, 0);
        }
        return itens.get(item);
    }

    public int getQuantidade() {
        return itens.values().stream().reduce(0, (proximo, acumulador) -> proximo + acumulador);
    }
    
    /**
     * Retorna todas as chaves do Map itens, ou seja, todos os objetos da classe CarrinhoItem
     * @return Uma coleção de CarrinhoItem
     */
    public Collection<CarrinhoItem> getItens() {
    	return itens.keySet();
    }
    
    public BigDecimal getTotal(CarrinhoItem item) {
    	return item.getTotal(getQuantidade(item));
    }
    
    public BigDecimal getTotal() {
    	BigDecimal total = BigDecimal.ZERO;
    	for (CarrinhoItem item : itens.keySet()) {
    		total = total.add(getTotal(item));
    	}
    	return total;
    }

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		produto.setId(produtoId);
		
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
}