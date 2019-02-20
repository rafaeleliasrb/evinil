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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.beblue.evinil.model.Item;
import br.com.beblue.evinil.model.Venda;
import br.com.beblue.evinil.repository.DiscoRepository;
import br.com.beblue.evinil.repository.VendaRepository;
import br.com.beblue.evinil.viewmodel.ItemViewModel;
import br.com.beblue.evinil.viewmodel.VendaViewModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

	@Autowired
	private DiscoRepository discoRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@GetMapping(value = "byData/", params = {"dataInicio", "dataFim", "page", "size"})
	public List<Venda> findAll(@RequestParam("dataInicio") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dataInicio, 
			@RequestParam("dataFim") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dataFim, 
			@RequestParam("page") int page, @RequestParam("size") int size) {
		if(dataInicio == null) dataInicio = LocalDate.now();
		if(dataFim == null) dataFim = LocalDate.now();
		
		return vendaRepository.findVendas(
				dataInicio.atStartOfDay(), dataFim.atStartOfDay(), 
				PageRequest.of(page, size, Sort.by(Direction.ASC, "data"))).stream()
			.collect(Collectors.toList());
	}
	
	@GetMapping("byId/{id}")
	public Venda findById(@PathVariable Long id) {
		return vendaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Venda não encontrado"));
	}
	
	@PostMapping
	public Venda save(@RequestBody VendaViewModel vendaViewModel) throws Exception {
		try {
			return vendaRepository.save(toVenda(vendaViewModel));
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new Exception("Erro ao salvar a venda");
		}
	}
	
	private Venda toVenda(VendaViewModel vendaViewModel) {
		Venda venda = new Venda();
		vendaViewModel.getItens().stream().forEach(item -> venda.adicionaItens(toItem(item)));
		return venda;
	}
	
	private Item toItem(ItemViewModel viewModel) {
		return new Item(discoRepository.findById(viewModel.getDisco())
				.orElseThrow(() -> new EntityNotFoundException("Disco com id: " + viewModel.getDisco() + " não encontrado")), 
			viewModel.getQuantidade());
	}
}
