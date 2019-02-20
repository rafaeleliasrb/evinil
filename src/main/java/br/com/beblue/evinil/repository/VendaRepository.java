package br.com.beblue.evinil.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.beblue.evinil.model.Venda;

public interface VendaRepository extends CrudRepository<Venda, Long> {

	@Query(value = "select v from Venda v left join fetch v.itens",
			countQuery = "select count(v.id) from Venda v")
	public Page<Venda> findVendas(
			LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);
	
	@EntityGraph(attributePaths = { "itens" })
	public Optional<Venda> findById(Long id);
}
