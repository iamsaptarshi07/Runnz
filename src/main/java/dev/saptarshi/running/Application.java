package dev.saptarshi.running;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Application started");
	}
	
//	 @Bean
//	 CommandLineRunner runner(RunRepository runRepository) {
//	 	return args -> {
//	 		Run run = new Run(
//	 			2,
//	 			"first run",
//	 		  	LocalDateTime.now(),
//	 		  	LocalDateTime.now().plus(1, ChronoUnit.HOURS),
//	 			5,
//	 			Location.OUTDOOR
//	 		);
//	 		runRepository.create(run);
//	 		log.info("Run created " + run);
//	 	};
//	 }

}