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
	
	/*public List<Disco> findDiscoByGenero(String genero, int page, int size) {
		return discoRepository.findByGeneroOrderByNomeAsc(GeneroMusical.byGenero(genero), new pageresul).stream()
				.collect(Collectors.toList());
	}*/
}
