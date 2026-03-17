package duriu.deton.duriuopen.duriuopen.service;

import duriu.deton.duriuopen.duriuopen.model.Letter;
import duriu.deton.duriuopen.duriuopen.repository.LetterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class LetterService {

    private final LetterRepository repository;

    public LetterService(LetterRepository repository) {
        this.repository = repository;
    }

    public Flux<Letter> findAll() {
        return repository.findAll();
    }

    public Mono<Letter> create(Letter letter) {
        if (letter.getCreatedAt() == null) {
            letter.setCreatedAt(LocalDateTime.now());
        }
        // Simple Gmail-only validation: must end with @gmail.com
        if (letter.getGmail() == null || !letter.getGmail().toLowerCase().endsWith("@gmail.com")) {
            return Mono.error(new IllegalArgumentException("Only gmail addresses are accepted"));
        }
        return repository.save(letter);
    }
}

