package com.example.algamoney.api;
  
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
 
@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyApiProperty.class)
public class AlgamoneyApiApplication {
 
	private static ApplicationContext APPLICATION_CONTEXT;
	
	public static void main(String[] args) {
	APPLICATION_CONTEXT =	SpringApplication.run(AlgamoneyApiApplication.class, args);
	}

	// Criado essa classe para conseguir pegar uma instancia de qualquer classe usando o JPA Hibernate ao inves do Spring. Nao preciso anotar com Autowired
	// Usado para retornar url no json dos arquivos anexados na AWS.
	public static <T> T getBean(Class<T> type){
		return APPLICATION_CONTEXT.getBean(type);
	}
	
}
