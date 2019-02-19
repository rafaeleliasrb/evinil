package br.com.beblue.evinil.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.beblue.evinil.enums.GeneroMusical;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Disco {

	@JsonProperty("id")
	@Id
	private String id;
	
	@JsonProperty("name")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private GeneroMusical genero;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getPreco() {
		return BigDecimal.valueOf(Math.random() * 49 + 1).setScale(2, RoundingMode.HALF_UP);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public GeneroMusical getGenero() {
		return genero;
	}
	
	public void setGenero(GeneroMusical genero) {
		this.genero = genero;
	}
}
