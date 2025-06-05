package tech.diogoalmeida.urlshortener.controller;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.diogoalmeida.urlshortener.controller.dto.ShortenUrlRequest;
import tech.diogoalmeida.urlshortener.controller.dto.ShortenUrlResponse;
import tech.diogoalmeida.urlshortener.entities.UrlEntity;
import tech.diogoalmeida.urlshortener.repository.UrlRepository;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping (value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request,
                                           HttpServletRequest serveletRequest){

        //Lógica de Id
        String id;
        //Caso caia um ID repetido gera outro registro
        do{
            id = RandomStringUtils.randomAlphanumeric(5, 10); //De forma aleatoria gerando chaves de 5 a 10 caracteres
        } while(urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(5))); //Irá se expirar de 5 em 5 minutos

        var redirectUrl = serveletRequest.getRequestURL().toString().replace("shorten-url", id);

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id){

        var url = urlRepository.findById(id);

        if (url.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers =new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

    }

}
