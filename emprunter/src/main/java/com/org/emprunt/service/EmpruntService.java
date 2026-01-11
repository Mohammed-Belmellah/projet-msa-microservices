package com.org.emprunt.service;

import com.org.emprunt.DTO.EmpruntDetailsDTO;
import com.org.emprunt.entities.Emprunter;
import com.org.emprunt.feign.BookClient;
import com.org.emprunt.feign.UserClient;
import com.org.emprunt.repositories.EmpruntRepository;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class EmpruntService {

    private final EmpruntRepository repo;
    private final UserClient userClient;
    private final BookClient bookClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmpruntService(EmpruntRepository repo, UserClient userClient, BookClient bookClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.repo = repo;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Emprunter createEmprunt(Long userId, Long bookId) {

        var user = userClient.getUser(userId);
        var book = bookClient.getBook(bookId);

        Emprunter b = new Emprunter();
        b.setUserId(userId);
        b.setBookId(bookId);
        Emprunter savedEmprunt = repo.save(b);

        String jsonMessage = String.format(
                "{\"empruntId\": %d, \"userId\": %d, \"bookId\": %d, \"eventType\": \"EMPRUNT_CREATED\", \"timestamp\": \"%s\"}",
                savedEmprunt.getId(),
                userId,
                bookId,
                java.time.LocalDateTime.now()
        );

        // On envoie sur le topic "emprunt-topic"
        kafkaTemplate.send("emprunt-created", jsonMessage);

        System.out.println("📤 Message envoyé à Kafka : " + jsonMessage);

        return savedEmprunt;
    }

    public List<EmpruntDetailsDTO> getAllEmprunts() {
        return repo.findAll().stream().map(e -> {

            var user = userClient.getUser(e.getUserId());
            var book = bookClient.getBook(e.getBookId());

            return new EmpruntDetailsDTO(
                    e.getId(),
                    user.getName(),
                    book.getTitre(),
                    e.getEmpruntDate());
        }).collect(Collectors.toList());
    }

}
