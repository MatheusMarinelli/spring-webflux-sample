//package br.com.spring.webflux.service;
//
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
///**
// * Esta classe é apenas a simulação da chamada da API do DeepL usando o WebClient
// */
//public class Traducao {
//    public static Mono<String> obterTraducao(String texto, String idioma) {
//        WebClient webClient = WebClient.builder()
//                .baseUrl("https://api-free.deepl.com/v2/translate")
//                .build();
//
//        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
//
//        request.add("text", texto);
//        request.add("target_lang", idioma);
//
//        return webClient.post() // metodo http
//                .header("Authorization", "DeepL-Auth-Key + KEY") // add os headers
//                .body(BodyInserters.fromFormData(request))// add o request body
//                .retrieve()
//                .bodyToMono(Object.class)// classe para converter o retorno da chamada
//                .map(Traduzido::getTexto); // pegar a propriedade 'texto' do retorno
//
//
//    }
//}
