package entrevista.tecnica.practicameli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableCaching
public class PracticameliApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticameliApplication.class, args);
	}

}
