package com.org.emprunt;

import com.org.emprunt.DTO.BookDTO;
import com.org.emprunt.DTO.UserDTO;
import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.feign.BookClient;
import com.org.emprunt.feign.UserClient;
import com.org.emprunt.repositories.EmpruntRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableFeignClients
public class EmpruntServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpruntServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initEmprunts(EmpruntRepository empruntRepository, UserClient userClient, BookClient bookClient) {
        return args -> {
            System.out.println("⏳ Tentative d'initialisation des emprunts avec vérification...");

            // --- CAS 1 : Alice (ID 1) emprunte Le Petit Prince (ID 1) ---
            try {
                // 1. Vérifications via Feign
                UserDTO user1 = userClient.getUser(1L);
                BookDTO book1 = bookClient.getBook(1L);

                if (user1 != null && book1 != null) {
                    Emprunter e1 = new Emprunter();
                    e1.setUserId(user1.getId());
                    e1.setBookId(book1.getId());
                    e1.setEmpruntDate(LocalDate.now());
                    empruntRepository.save(e1);
                    System.out.println("Emprunt créé : " + user1.getName() + " -> " + book1.getTitre());
                }
            } catch (Exception e) {
                System.out.println("⚠Impossible de créer l'emprunt 1 (Services peut-être indisponibles) : " + e.getMessage());
            }

            // --- CAS 2 : Bob (ID 2) emprunte L'Étranger (ID 2) ---
            try {
                UserDTO user2 = userClient.getUser(2L);
                BookDTO book2 = bookClient.getBook(2L);

                if (user2 != null && book2 != null) {
                    Emprunter e2 = new Emprunter();
                    e2.setUserId(user2.getId());
                    e2.setBookId(book2.getId());
                    e2.setEmpruntDate(LocalDate.now().minusDays(5));
                    empruntRepository.save(e2);
                    System.out.println("Emprunt créé : " + user2.getName() + " -> " + book2.getTitre());
                }
            } catch (Exception e) {
                System.out.println("⚠Impossible de créer l'emprunt 2 : " + e.getMessage());
            }
        };
    }
}
