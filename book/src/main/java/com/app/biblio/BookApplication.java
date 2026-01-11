package com.app.biblio;

import com.app.biblio.entities.Book;
import com.app.biblio.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Bean
	CommandLineRunner initBooks(BookRepository bookRepository) {
		return args -> {
			// Livre 1
			Book b1 = new Book();
			b1.setTitre("Le Petit Prince");
			bookRepository.save(b1);

			// Livre 2
			Book b2 = new Book();
			b2.setTitre("L'Étranger");
			bookRepository.save(b2);

			// Livre 3
			Book b3 = new Book();
			b3.setTitre("Les Misérables");
			bookRepository.save(b3);

			System.out.println(" Données Book initialisées !");
		};
	}

}
