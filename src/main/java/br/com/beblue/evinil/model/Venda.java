package br.com.beblue.evinil.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime data;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "venda_id")
	private List<Item> itens = new ArrayList<>();
	
	private BigDecimal total;
	
	private BigDecimal desconto;
	
	@PrePersist
	public void prePersist() {
		data = LocalDateTime.now();
		total = itens.stream().map(item -> item.getDisco().getPreco())
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_DOWN);
		desconto = itens.stream().map(item -> item.getDisco().getPreco()
				.multiply(BigDecimal.valueOf(item.getQuantidade()))
				.multiply(BigDecimal.valueOf(Cashback.findByGeneroEDiaDaSemana(item.getDisco().getGenero(), data.getDayOfWeek())))
				.divide(BigDecimal.valueOf(100)))
			.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_DOWN);
	}
	
	public Long getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public List<Item> getItens() {
		return Collections.unmodifiableList(itens);
	}

	public void adicionaItens(Item item) {
		itens.add(item);
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}
	
	@JsonProperty("a_pagar")
	public BigDecimal getAPagar() {
		return total.subtract(desconto);
	}
}
