package br.com.beblue.evinil.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = false, updatable = false, insertable = true)
	private Venda venda;*/
	
	@ManyToOne
	private Disco disco;
	
	private int quantidade;

	public Item() {}
	
	public Item(Disco disco, int quantidade) {
		this.disco = disco;
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}

	/*public Venda getVenda() {
		return venda;
	}*/
	
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
}
