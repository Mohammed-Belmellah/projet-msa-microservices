package com.org.biblio;

import com.org.biblio.entities.User;
import com.org.biblio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	CommandLineRunner initUsers(UserRepository userRepository) {
		return args -> {
			// Utilisateur 1
			User u1 = new User();
			u1.setName("Alice Dupont");
			u1.setEmail("alice@gmail.com");
			userRepository.save(u1);

			// Utilisateur 2
			User u2 = new User();
			u2.setName("Bob Martin");
			u2.setEmail("bob@outlook.com");
			userRepository.save(u2);

			System.out.println("Données User initialisées !");
		};
	}

}
