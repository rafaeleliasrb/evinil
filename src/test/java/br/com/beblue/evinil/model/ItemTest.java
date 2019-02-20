package br.com.beblue.evinil.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Test;

import br.com.beblue.evinil.data.Cashback;
import br.com.beblue.evinil.enums.GeneroMusical;

public class ItemTest {

	@Test
	public void valorCashback() {
		Disco disco = new Disco();
		GeneroMusical mpb = GeneroMusical.MPB;
		disco.setGenero(mpb);
		BigDecimal preco = disco.getPreco();
		BigDecimal porcentagemCashback = BigDecimal
				.valueOf(Cashback.findByGeneroEDiaDaSemana(mpb, LocalDate.now().getDayOfWeek()))
				.divide(BigDecimal.valueOf(100));
		
		Item item = new Item(disco, 1);
		
		assertEquals(
				preco.multiply(porcentagemCashback).multiply(BigDecimal.valueOf(item.getQuantidade()))
					.setScale(2, RoundingMode.HALF_DOWN), 
				item.getCashback());
	}
}
