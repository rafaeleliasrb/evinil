package br.com.beblue.evinil.thirdpartapi;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
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
import br.com.beblue.evinil.model.DiscosGenero;

@Component
public class SpotifyApi {

	@Value("${spotify.client.id}")
	private String clientId;
	
	@Value("${spotify.client.secret}")
	private String clientSecret;
	
	private Token token;
	
	private RestTemplate restTemplate;
	
	public SpotifyApi(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public DiscosGenero findDiscoByGenero(GeneroMusical genero) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", "Bearer " + getToken().getAccessToken());
	
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
			return objectMapper.readValue(responseEntity.getBody(), DiscosGenero.class);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível recuperar os discos pelo gênero");
		}
	}
	
	private Token getToken() {
		if(token == null || token.isExpirado()) 
			return geraNovoToken();
		return token;
	}
	
	private Token geraNovoToken() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			String chave = clientId + ":" + clientSecret;
			headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(chave.getBytes()));
	
			HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);
	
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://accounts.spotify.com/api/token", 
					HttpMethod.POST, entity, String.class);
		
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(responseEntity.getBody(), Token.class);
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível recuperar o token de acesso");
		}
	}
}
