package br.com.beblue.evinil.enums;

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
	
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return id;
	}
}
