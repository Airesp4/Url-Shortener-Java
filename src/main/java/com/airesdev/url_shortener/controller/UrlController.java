package com.airesdev.url_shortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airesdev.url_shortener.docs.UrlControllerDocs;
import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.model.Url;
import com.airesdev.url_shortener.service.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController implements UrlControllerDocs{
    
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Url> createShortUrl(@RequestBody UrlDTO urlDto) {

        return ResponseEntity.ok(urlService.save(urlDto));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {

        return ResponseEntity.ok(urlService.findByShortUrl(shortUrl).getOriginalUrl());
    }
}
