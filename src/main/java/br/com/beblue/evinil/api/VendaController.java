package br.com.beblue.evinil.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.beblue.evinil.model.Venda;
import br.com.beblue.evinil.repository.VendaRepository;
import br.com.beblue.evinil.viewmodel.Mapper;
import br.com.beblue.evinil.viewmodel.VendaViewModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

	@Autowired
	private Mapper mapper;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	public VendaController(Mapper mapper, VendaRepository vendaRepository) {
		this.mapper = mapper;
		this.vendaRepository = vendaRepository;
	}
	
	@GetMapping(value = "byData/", params = {"dataInicio", "dataFim", "page", "size"})
	public List<Venda> findAll(@RequestParam("dataInicio") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dataInicio, 
			@RequestParam("dataFim") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dataFim, 
			@RequestParam("page") int page, @RequestParam("size") int size) {
		if(dataInicio == null) dataInicio = LocalDate.now();
		if(dataFim == null) dataFim = LocalDate.now();
		
		return vendaRepository.findByDataGreaterThanEqualAndDataLessThanEqual(
				dataInicio.atStartOfDay(), dataFim.atTime(23, 59, 59), 
				PageRequest.of(page, size, Sort.by(Direction.ASC, "data"))).stream()
			.collect(Collectors.toList());
	}
	
	@GetMapping("byId/{id}")
	public Venda findById(@PathVariable Long id) {
		return vendaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Venda não encontrado"));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Venda save(@RequestBody VendaViewModel vendaViewModel) throws Exception {
		try {
			return vendaRepository.save(mapper.toVenda(vendaViewModel));
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Erro ao salvar a venda", e);
		}
	}
}
