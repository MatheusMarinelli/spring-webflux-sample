package br.com.spring.webflux.repository;


import br.com.spring.webflux.domain.Evento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventoRespository extends ReactiveCrudRepository<Evento, Long> {
}
