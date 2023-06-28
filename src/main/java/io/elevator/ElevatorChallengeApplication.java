package io.elevator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ElevatorChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElevatorChallengeApplication.class, args);
	}

}
