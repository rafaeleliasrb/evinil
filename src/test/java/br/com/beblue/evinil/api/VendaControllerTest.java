package br.com.beblue.evinil.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;
import br.com.beblue.evinil.model.Item;
import br.com.beblue.evinil.model.Venda;
import br.com.beblue.evinil.repository.DiscoRepository;
import br.com.beblue.evinil.repository.VendaRepository;
import br.com.beblue.evinil.viewmodel.ItemViewModel;
import br.com.beblue.evinil.viewmodel.Mapper;
import br.com.beblue.evinil.viewmodel.VendaViewModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebMvcTest(VendaController.class)
public class VendaControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private Mapper mapper;
    
    @MockBean
    private DiscoRepository discoRepository;
    
    @MockBean
    private VendaRepository vendaRepository;
    
    private Venda venda;

	private Disco disco1;

	private Disco disco2;
	
	@Before
	public void inicializar() throws Exception {
		venda = new Venda();
		
		Field field = Disco.class.getDeclaredField("id");
		field.setAccessible(true);
		
		disco1 = new Disco();
		disco1.setGenero(GeneroMusical.MPB);
		field.set(disco1, "1SBKxrKIIgGyYMl43ffjnU");
		disco2 = new Disco();
		disco2.setGenero(GeneroMusical.ROCK);
		field.set(disco1, "3V7cJKYn9Da5gv601HuchP");
		
		Item item1 = new Item(disco1, 1);
		Item item2 = new Item(disco2, 2);
		
		venda.adicionaItens(item1);
		venda.adicionaItens(item2);
	}
    
    @Test
    public void adicionarVenda() throws Exception {
    	
    	ObjectMapper mapperJson = new ObjectMapper();
		
		ItemViewModel itemViewModel1 = new ItemViewModel();
		itemViewModel1.setDisco(disco1.getId());
		itemViewModel1.setQuantidade(1);
		
		ItemViewModel itemViewModel2 = new ItemViewModel();
		itemViewModel2.setDisco(disco2.getId());
		itemViewModel2.setQuantidade(2);
		
		VendaViewModel vendaViewModel = new VendaViewModel();
		vendaViewModel.setItens(Arrays.asList(itemViewModel1, itemViewModel2));
		
		String jsonInString = mapperJson.writeValueAsString(vendaViewModel);
		
		when(mapper.toVenda(vendaViewModel)).thenReturn(venda);
		when(vendaRepository.save(venda)).thenReturn(venda);
		
    	MockHttpServletResponse response = mockMvc.perform(
                post("/api/vendas/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonInString
                )).andReturn().getResponse();
    	Assert.assertEquals(response.getStatus(), HttpStatus.CREATED.value());
    }
    
}
