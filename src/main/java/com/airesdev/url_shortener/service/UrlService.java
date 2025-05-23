package com.airesdev.url_shortener.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.model.Url;
import com.airesdev.url_shortener.repository.UrlRepository;


@Service
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
        return Base64.getUrlEncoder().withoutPadding()
            .encodeToString(UUID.nameUUIDFromBytes(originalUrl.getBytes()).toString().getBytes())
            .substring(0, 9);
    }
}
