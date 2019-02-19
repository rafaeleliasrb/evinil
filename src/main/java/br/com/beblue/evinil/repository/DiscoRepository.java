package br.com.beblue.evinil.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;

@Repository
public interface DiscoRepository extends CrudRepository<Disco, String>{

	public List<Disco> findByGenero(GeneroMusical genero);
	
}
