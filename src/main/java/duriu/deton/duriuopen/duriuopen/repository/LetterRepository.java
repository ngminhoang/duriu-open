package duriu.deton.duriuopen.duriuopen.repository;

import duriu.deton.duriuopen.duriuopen.model.Letter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LetterRepository extends ReactiveCrudRepository<Letter, Long> {
}

