package com.airesdev.url_shortener.service;

import java.util.UUID;

import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.model.Url;
import com.airesdev.url_shortener.repository.UrlRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UrlService {
    
    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public Url save(UrlDTO urlDto) {
        var url = new Url(UUID.randomUUID(), urlDto.originalUrl(), generateShortUrl(urlDto.originalUrl()));
        return repository.save(url);
    }

    public Url findByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
            .orElseThrow(() -> new RuntimeException("Short URL não encontrada"));
    }
    
    private String generateShortUrl(String originalUrl) {
        String hash = BCrypt.withDefaults().hashToString(12, (originalUrl + System.currentTimeMillis()).toCharArray());

        return hash.replaceAll("[^a-zA-Z0-9]", "").substring(0, 8);
    }
}
