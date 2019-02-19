package br.com.beblue.evinil.enums;

import java.util.Arrays;

public enum GeneroMusical {
	POP("Pop", 1),
	MPB("Mpb", 2),
	CLASSIC("Classic", 3),
	ROCK("Rock", 4);
	
	private String nome;
	private int id;
	
	private GeneroMusical(String nome, int id) {
		this.nome = nome;
		this.id = id;
	}
	
	public static GeneroMusical byGenero(String genero) {
		return Arrays.stream(values())
			.filter(g -> g.nome.equalsIgnoreCase(genero)).findFirst()
			.orElseThrow(() -> new RuntimeException("Nenhum genero encontrado para " + genero));
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return id;
	}
}
