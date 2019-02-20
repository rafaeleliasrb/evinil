package br.com.beblue.evinil.viewmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.beblue.evinil.model.Disco;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "albums")
public class DiscosGeneroViewModel {

	@JsonProperty("items")
	private List<Disco> discos;

	public List<Disco> getDiscos() {
		return discos;
	}

	public void setDiscos(List<Disco> discos) {
		this.discos = discos;
	}
}
