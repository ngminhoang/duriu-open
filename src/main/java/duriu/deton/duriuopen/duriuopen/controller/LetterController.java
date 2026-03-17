package duriu.deton.duriuopen.duriuopen.controller;

import duriu.deton.duriuopen.duriuopen.model.Letter;
import duriu.deton.duriuopen.duriuopen.service.LetterService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/letters", produces = MediaType.APPLICATION_JSON_VALUE)
public class LetterController {

    private final LetterService service;

    public LetterController(LetterService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Letter> list() {
        return service.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@RequestBody Letter letter) {
        return service.create(letter)
                .map(saved -> ResponseEntity.ok().body((Object) saved))
                .onErrorResume(IllegalArgumentException.class, ex -> Mono.just(ResponseEntity.badRequest().body((Object) ex.getMessage())));
    }
}



