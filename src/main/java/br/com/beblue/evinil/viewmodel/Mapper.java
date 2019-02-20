package br.com.beblue.evinil.viewmodel;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.beblue.evinil.model.Item;
import br.com.beblue.evinil.model.Venda;
import br.com.beblue.evinil.repository.DiscoRepository;

@Component
public class Mapper {

	private DiscoRepository discoRepository;
	
	@Autowired
	public Mapper(DiscoRepository discoRepository) {
		this.discoRepository = discoRepository;
	}
	
	public Venda toVenda(VendaViewModel vendaViewModel) {
		Venda venda = new Venda();
		vendaViewModel.getItens().stream().forEach(item -> venda.adicionaItens(toItem(item)));
		return venda;
	}
	
	public Item toItem(ItemViewModel viewModel) {
		return new Item(discoRepository.findById(viewModel.getDisco())
				.orElseThrow(() -> new EntityNotFoundException("Disco com id: " + viewModel.getDisco() + " não encontrado")), 
			viewModel.getQuantidade());
	}
}
