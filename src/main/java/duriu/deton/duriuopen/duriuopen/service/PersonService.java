package duriu.deton.duriuopen.duriuopen.service;

import duriu.deton.duriuopen.duriuopen.model.Person;
import duriu.deton.duriuopen.duriuopen.repository.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Flux<Person> findAll() {
        return repository.findAll();
    }

    public Mono<Person> findById(Long id) {
        return repository.findById(id);
    }

    public Mono<Person> create(Person person) {
        if (person.getCreatedAt() == null) {
            person.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(person);
    }

    public Mono<Person> update(Long id, Person person) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(person.getName());
                    existing.setEmail(person.getEmail());
                    existing.setAge(person.getAge());
                    return repository.save(existing);
                });
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}

