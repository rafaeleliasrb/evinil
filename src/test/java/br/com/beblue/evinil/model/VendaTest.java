package br.com.beblue.evinil.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.beblue.evinil.enums.GeneroMusical;

public class VendaTest {

	private Venda venda;
	private Item item1;
	private Item item2;
	
	@Before
	public void inicializar() {
		venda = new Venda();
		
		Disco disco1 = new Disco();
		disco1.setGenero(GeneroMusical.MPB);
		Disco disco2 = new Disco();
		disco2.setGenero(GeneroMusical.ROCK);
		
		item1 = new Item(disco1, 1);
		item2 = new Item(disco2, 2);
	}
	
	@Test
	public void adicionarItem() {
		venda.adicionaItens(item1);
		venda.adicionaItens(item2);
		
		assertEquals(2, venda.getItens().size());
		assertEquals(true, venda.getItens().contains(item1));
		assertEquals(true, venda.getItens().contains(item2));
	}
	
	@Test
	public void valorTotal() {
		BigDecimal preco1 = item1.getValor();
		BigDecimal preco2 = item2.getValor();
		
		venda.adicionaItens(item1);
		venda.adicionaItens(item2);
		
		assertEquals(preco1.add(preco2), venda.getValorTotal());
	}
	
	@Test
	public void cashbackTotal() {
		BigDecimal cashback1 = item1.getCashback();
		BigDecimal cashback2 = item2.getCashback();

		venda.adicionaItens(item1);
		venda.adicionaItens(item2);
		
		assertEquals(cashback1.add(cashback2), venda.getCashbackTotal());
	}
}
