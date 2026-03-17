package duriu.deton.duriuopen.duriuopen.controller;

import duriu.deton.duriuopen.duriuopen.model.Person;
import duriu.deton.duriuopen.duriuopen.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Person> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Person> get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mono<Person> create(@RequestBody Person person) {
        return service.create(person);
    }

    @PutMapping("/{id}")
    public Mono<Person> update(@PathVariable Long id, @RequestBody Person person) {
        return service.update(id, person);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}

