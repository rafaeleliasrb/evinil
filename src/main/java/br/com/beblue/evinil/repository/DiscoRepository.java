package br.com.beblue.evinil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;

@Repository
public interface DiscoRepository extends CrudRepository<Disco, String>{

	public Page<Disco> findByGenero(GeneroMusical genero, Pageable pageable);
	
}
