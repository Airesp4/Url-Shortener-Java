package com.airesdev.url_shortener.service;

import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.exception.ShortUrlNotFoundException;
import com.airesdev.url_shortener.model.Url;
import com.airesdev.url_shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UrlService {
    
    private final UrlRepository repository;

    public Url save(UrlDTO urlDto) {
        UUID id = UUID.randomUUID();

        String shortUrl = generateShortUrl(urlDto.originalUrl(), id);

        Url url = new Url(id, urlDto.originalUrl(), shortUrl);
        return repository.save(url);
    }

    public Url findByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
            .orElseThrow(() -> new ShortUrlNotFoundException(shortUrl));
    }
    
    private String generateShortUrl(String originalUrl, UUID id) {
        String input = originalUrl + id.toString();

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(
                UUID.nameUUIDFromBytes(input.getBytes()).toString().getBytes()
            )
            .substring(0, 9);
    }
}
