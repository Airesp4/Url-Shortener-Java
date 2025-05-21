package com.airesdev.url_shortener.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.airesdev.url_shortener.model.Url;

public interface UrlRepository extends MongoRepository<Url, UUID> {
    
    Optional<Url> findByShortUrl(String shortUrl);
}
