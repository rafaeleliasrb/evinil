package br.com.beblue.evinil.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Disco disco;
	
	private int quantidade;
	
	private BigDecimal desconto;

	public Item() {}
	
	public Item(Disco disco, int quantidade) {
		this.disco = disco;
		this.quantidade = quantidade;
	}
	
	public void calculaDesconto() {
		LocalDateTime data = LocalDateTime.now();
		desconto = disco.getPreco()
				.multiply(BigDecimal.valueOf(quantidade))
				.multiply(BigDecimal.valueOf(Cashback.findByGeneroEDiaDaSemana(disco.getGenero(), data.getDayOfWeek())))
				.divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_DOWN);
	}

	public Long getId() {
		return id;
	}
	
	public Disco getDisco() {
		return disco;
	}

	public void setDisco(Disco disco) {
		this.disco = disco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}
}
