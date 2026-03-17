package duriu.deton.duriuopen.duriuopen.repository;

import duriu.deton.duriuopen.duriuopen.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
    Mono<Person> findByEmail(String email);
}

