package br.com.beblue.evinil.thirdpartapi;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TokenFactory {

	@Value("${spotify.client.id}")
	private String clientId;
		
	@Value("${spotify.client.secret}")
	private String clientSecret;
	
	private Token token;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public TokenFactory(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public Token getToken() {
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
