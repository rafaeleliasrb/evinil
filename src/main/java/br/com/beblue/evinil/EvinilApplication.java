package br.com.beblue.evinil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import br.com.beblue.evinil.data.Database;

@SpringBootApplication
public class EvinilApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvinilApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	@Profile("!test")
	public CommandLineRunner initData(Database database){
	   return args -> database.carregaDiscosBancoDados();
	}
}
