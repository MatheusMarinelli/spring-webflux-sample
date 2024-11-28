package br.com.spring.webflux.controller;

import br.com.spring.webflux.domain.EventoDTO;
import br.com.spring.webflux.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {


    private final EventoService service;

    // objeto responsável por pegar novos dados cadastrados e enviar aos clientes conectados à aplicação
    private final Sinks.Many sink;

    public EventoController(EventoService service) {
        this.service = service;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }



    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDTO> obterTodos() {

        // devolve 1 elemento a cada 4 segundos para visualizar como funciona o SSE (server-sent events)
        // devolve novos eventos cadastrados devido ao objeto 'sink' que realiza essa atualização em tempo real por ser reativo
        return Flux.merge(service.obterTodos(), sink.asFlux()).delayElements(Duration.ofSeconds(4));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventoDTO> cadastrar(@RequestBody EventoDTO eventoDTO) {
        return service.cadastrar(eventoDTO).doOnSuccess(evt -> sink.tryEmitNext(evt));
    }



    @GetMapping("/{id}")
    public Mono<EventoDTO> obterTodos(@PathVariable Long id) {
        return service.obterPorId(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return service.excluir(id);
    }



}
