package br.com.beblue.evinil.model;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import br.com.beblue.evinil.enums.GeneroMusical;

public class Cashback {

	private static final Map<GeneroMusical, Integer[]> cashbacks = new HashMap<>();
	private static final Integer[] pop = new Integer[]{0, 7, 6, 2, 10, 15, 20, 25};
	private static final Integer[] mpb = new Integer[]{0, 5, 10, 15, 20, 25, 30, 30};
	private static final Integer[] classic = new Integer[]{0, 3, 5, 8, 13, 18, 25, 35};
	private static final Integer[] rock = new Integer[]{0, 10, 15, 15, 15, 20, 40, 40};
	
	static {
		cashbacks.put(GeneroMusical.POP, pop);
		cashbacks.put(GeneroMusical.MPB, mpb);
		cashbacks.put(GeneroMusical.CLASSIC, classic);
		cashbacks.put(GeneroMusical.ROCK, rock);
	}
	
	private Cashback() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Integer findByGeneroEDiaDaSemana(GeneroMusical genero, DayOfWeek diasDaSemana) {
		return cashbacks.get(genero)[diasDaSemana.getValue()];
	}
}
