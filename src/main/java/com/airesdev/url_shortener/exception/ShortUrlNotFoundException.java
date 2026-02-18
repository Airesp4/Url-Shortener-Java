package com.airesdev.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortUrl) {
        super("Short URL não encontrada: " + shortUrl);
    }
}
