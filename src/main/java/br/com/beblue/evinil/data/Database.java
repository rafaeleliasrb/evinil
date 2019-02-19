package br.com.beblue.evinil.data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.repository.DiscoRepository;
import br.com.beblue.evinil.thirdpartapi.SpotifyApi;

@Component
public class Database {

	private SpotifyApi api;
	private DiscoRepository discoRepository;
	
	@Autowired
	public Database(SpotifyApi api, DiscoRepository discoRepository) {
		this.api = api;
		this.discoRepository = discoRepository;
	}
	
	public void carregaDiscosBancoDados() {
		Arrays.stream(GeneroMusical.values()).flatMap(genero -> 
				api.findDiscoByGenero(genero).getDiscos().stream().map(disco -> { 
					disco.setGenero(genero);
					return disco;
				}))
			.forEach(disco -> discoRepository.save(disco));
		/*discosByGenero.put(GeneroMusical.POP, api.findDiscoByGenero(GeneroMusical.POP).getDiscos());
		discosByGenero.put(GeneroMusical.CLASSIC, api.findDiscoByGenero(GeneroMusical.CLASSIC).getDiscos());
		discosByGenero.put(GeneroMusical.MPB, api.findDiscoByGenero(GeneroMusical.MPB).getDiscos());
		discosByGenero.put(GeneroMusical.ROCK, api.findDiscoByGenero(GeneroMusical.ROCK).getDiscos());*/
	}
}
