package br.com.beblue.evinil.model;

import java.math.BigDecimal;
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
	private LocalDateTime data = LocalDateTime.now();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "venda_id")
	private List<Item> itens = new ArrayList<>();
	
	private BigDecimal total = BigDecimal.ZERO;
	
	private BigDecimal desconto = BigDecimal.ZERO;
	
	public Long getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public List<Item> getItens() {
		return Collections.unmodifiableList(itens);
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
	
	public void adicionaItens(Item item) {
		item.calculaDesconto();
		atualizaTotal(item);
		atualizaDesconto(item);
		itens.add(item);
	}
	
	private void atualizaDesconto(Item novoItem) {
		desconto = desconto.add(novoItem.getDesconto());
	}
	
	private void atualizaTotal(Item novoItem) {
		total = total.add(novoItem.getDisco().getPreco());
	}
}
