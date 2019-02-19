package br.com.beblue.evinil.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;
import br.com.beblue.evinil.repository.DiscoRepository;

@Service
public class DiscoService {

	private DiscoRepository discoRepository;

	@Autowired
	public DiscoService(DiscoRepository discoRepository) {
		this.discoRepository = discoRepository;
	}
	
	public List<Disco> findDiscoByGenero(String genero) {
		return discoRepository.findByGenero(GeneroMusical.byGenero(genero)).stream()
				.sorted((d1, d2)->d1.getNome().compareTo(d2.getNome()))
				.collect(Collectors.toList());
	}
}
