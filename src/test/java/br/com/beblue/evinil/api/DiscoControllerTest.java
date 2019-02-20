package br.com.beblue.evinil.api;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.beblue.evinil.enums.GeneroMusical;
import br.com.beblue.evinil.model.Disco;
import br.com.beblue.evinil.repository.DiscoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebMvcTest(DiscoController.class)
public class DiscoControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscoRepository repository;
    
    @Test
    public void discosById() throws Exception {
    	String id = "1SBKxrKIIgGyYMl43ffjnU";
    	Disco disco = new Disco();
    	disco.setGenero(GeneroMusical.POP);
    	Field field = Disco.class.getDeclaredField("id");
		field.setAccessible(true);
		field.set(disco, id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(disco);
    	
        when(repository.findById(id)).thenReturn(Optional.of(disco));
        
        this.mockMvc.perform(get("/api/discos/byId/"+id))
        	.andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(jsonInString)));
    }
    
    @Test
    public void discosByGeneroPaginado() throws Exception {
    	String id = "1SBKxrKIIgGyYMl43ffjnU";
    	Disco disco = new Disco();
    	disco.setGenero(GeneroMusical.POP);
    	Field field = Disco.class.getDeclaredField("id");
		field.setAccessible(true);
		field.set(disco, id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(Arrays.asList(disco));
    	
        when(repository.findByGenero(disco.getGenero(), PageRequest.of(0, 5, Sort.by(Direction.ASC, "nome"))))
        	.thenReturn(new PageImpl<>(Arrays.asList(disco)));
        
        this.mockMvc.perform(get("/api/discos/byGenero/" + disco.getGenero().getNome() + "?page=0&size=5"))
        	.andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(jsonInString)));
    }
}
