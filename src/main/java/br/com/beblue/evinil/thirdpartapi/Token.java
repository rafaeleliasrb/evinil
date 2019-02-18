package br.com.beblue.evinil.thirdpartapi;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

	@JsonProperty(value = "access_token")
	private String accessToken;
	
	@JsonProperty(value = "token_type")
	private String tokenType;
	
	private LocalDateTime dataValidade;
	
	@JsonProperty(value = "expires_in")
	public void setDataValidade(int expiresIn) {
		dataValidade = LocalDateTime.now().plusMinutes(expiresIn/60);
	}
	
	public boolean isExpirado() {
		if(dataValidade!=null)
			return LocalDateTime.now().isAfter(dataValidade);
		return false;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
}
