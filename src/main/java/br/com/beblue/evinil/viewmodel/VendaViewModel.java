package br.com.beblue.evinil.viewmodel;

import java.util.List;

public class VendaViewModel {

	private List<ItemViewModel> itens;
	
	public void setItens(List<ItemViewModel> itens) {
		this.itens = itens;
	}
	
	public List<ItemViewModel> getItens() {
		return itens;
	}
}
