package afeka.ac.il.timersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimersServiceApplication.class, args);
	}

}
