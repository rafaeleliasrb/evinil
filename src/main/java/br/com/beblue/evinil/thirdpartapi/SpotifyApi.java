package br.com.beblue.evinil.thirdpartapi;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.viewmodel.DiscosGeneroViewModel;

@Component
public class SpotifyApi {

	private RestTemplate restTemplate;
	
	private TokenFactory tokenFactory;
	
	@Autowired
	public SpotifyApi(RestTemplate restTemplate, TokenFactory tokenFactory) {
		this.restTemplate = restTemplate;
		this.tokenFactory = tokenFactory;
	}
	
	public DiscosGeneroViewModel findDiscoByGenero(GeneroMusical genero) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer " + tokenFactory.getToken().getAccessToken());
	
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
			        .queryParam("q", genero.getNome())
			        .queryParam("type", "album")
			        .queryParam("market", "BR")
			        .queryParam("limit", 50);
			
			HttpEntity<String> entity = new HttpEntity<>(headers);
	
			ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), 
					HttpMethod.GET, entity, String.class);
		
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			return objectMapper.readValue(responseEntity.getBody(), DiscosGeneroViewModel.class);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível recuperar os discos pelo gênero");
		}
	}
}
