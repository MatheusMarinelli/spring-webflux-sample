package br.com.spring.webflux;

import br.com.spring.webflux.domain.EventoDTO;
import br.com.spring.webflux.domain.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebfluxApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void cadastrarNovoEvento() {

        EventoDTO dto = new EventoDTO(null, TipoEvento.SHOW, "Guns n Roses", LocalDate.parse("2025-01-01"), "descricao");

        webTestClient.post().uri("/eventos").bodyValue(dto)
                .exchange() // realiza a transação
                .expectStatus().isCreated()
                .expectBody(EventoDTO.class)
                .value(response -> { // asserts
                    assertNotNull(response.id());
                    assertEquals(dto.id(), response.id());
                    assertEquals(dto.nome(), response.nome());
                    assertEquals(dto.tipo(), response.tipo());
                    assertEquals(dto.data(), response.data());
                    assertEquals(dto.descricao(), response.descricao());
                });

    }


    @Test
    void buscarEvento() {

        EventoDTO dto = new EventoDTO(1L, TipoEvento.SHOW, "Taylor Swift", LocalDate.parse("2024-02-15"), "Um evento imperdível para todos os amantes da música pop.");

        webTestClient.get().uri("/eventos")
                .exchange() // realiza a transação
                .expectStatus().is2xxSuccessful()
                .expectBodyList(EventoDTO.class)
                .value(response -> { // asserts
                    EventoDTO responseEvento = response.get(0);
                    assertNotNull(responseEvento.id());
                    assertEquals(dto.id(), responseEvento.id());
                    assertEquals(dto.nome(), responseEvento.nome());
                    assertEquals(dto.tipo(), responseEvento.tipo());
                    assertEquals(dto.data(), responseEvento.data());
                    assertEquals(dto.descricao(), responseEvento.descricao());
                });

    }

}
