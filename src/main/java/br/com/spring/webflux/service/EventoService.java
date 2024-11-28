package br.com.spring.webflux.service;

import br.com.spring.webflux.domain.EventoDTO;
import br.com.spring.webflux.repository.EventoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRespository respository;

    public Flux<EventoDTO> obterTodos() {
        return respository.findAll()
                .map(EventoDTO::mapDto);
    }

    public Mono<EventoDTO> obterPorId(Long id) {
        return respository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))) // se o retorno for vazio lança uma exceção
                .map(EventoDTO::mapDto);
    }

    public Mono<EventoDTO> cadastrar(EventoDTO eventoDTO) {
        return respository.save(eventoDTO.toEntity()).map(EventoDTO::mapDto);
    }

    public Mono<Void> excluir(Long id) {
        return respository.findById(id).flatMap(respository::delete);
    }
}
