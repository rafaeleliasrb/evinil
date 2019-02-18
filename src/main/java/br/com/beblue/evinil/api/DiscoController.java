package br.com.beblue.evinil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.DiscosGenero;
import br.com.beblue.evinil.thirdpartapi.SpotifyApi;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/discos")
public class DiscoController {

	@Autowired
	private SpotifyApi spotifyApi;

	@GetMapping
	public void teste() throws Exception {
		DiscosGenero findDiscoByGenero = spotifyApi.findDiscoByGenero(GeneroMusical.POP);
		System.out.println(findDiscoByGenero);
	}
}
