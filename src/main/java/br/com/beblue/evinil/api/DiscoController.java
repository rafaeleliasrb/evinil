package br.com.beblue.evinil.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;
import br.com.beblue.evinil.repository.DiscoRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/discos")
public class DiscoController {

	private DiscoRepository discoRepository;

	@Autowired
	public DiscoController(DiscoRepository discoRepository) {
		this.discoRepository = discoRepository;
	}

	@GetMapping("byGenero/{genero}")
	public List<Disco> findDiscos(@PathVariable String genero) {
		return discoRepository.findByGenero(GeneroMusical.byGenero(genero)).stream()
				.sorted((d1, d2)->d1.getNome().compareTo(d2.getNome()))
				.collect(Collectors.toList());
	}
	
	@GetMapping("byId/{id}")
	public Disco findDiscoById(@PathVariable String id) {
		return discoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Disco não encontrado"));
	}
}
