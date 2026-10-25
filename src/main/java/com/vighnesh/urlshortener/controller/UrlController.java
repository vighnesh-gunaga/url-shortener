package com.vighnesh.urlshortener.controller;

import com.vighnesh.urlshortener.dto.CreateUrlRequest;
import com.vighnesh.urlshortener.dto.CreateUrlResponse;
import com.vighnesh.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UrlController {

    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/test")
    public String test() {
        return "URL Shortener API is working!";
    }

    @PostMapping("/api/urls")
    public CreateUrlResponse createShortUrl(@RequestBody CreateUrlRequest request)
    {
        String shortCode = urlShortenerService.createShortUrl(request.getOriginalUrl());

        String shortUrl = "http://localhost:8081/"+shortCode;

        return new CreateUrlResponse(shortCode,shortUrl);
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }
}
