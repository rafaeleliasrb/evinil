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
	
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private BigDecimal cashbackTotal = BigDecimal.ZERO;
	
	public Long getId() {
		return id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public List<Item> getItens() {
		return Collections.unmodifiableList(itens);
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public BigDecimal getCashbackTotal() {
		return cashbackTotal;
	}
	
	public void adicionaItens(Item item) {
		atualizaValorTotal(item);
		atualizaCashbackTotal(item);
		itens.add(item);
	}
	
	private void atualizaCashbackTotal(Item novoItem) {
		cashbackTotal = cashbackTotal.add(novoItem.getCashback());
	}
	
	private void atualizaValorTotal(Item novoItem) {
		valorTotal = valorTotal.add(novoItem.getValor());
	}
}
