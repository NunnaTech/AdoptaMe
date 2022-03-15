package mx.com.adoptame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AdoptaMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptaMeApplication.class, args);
	}

}
