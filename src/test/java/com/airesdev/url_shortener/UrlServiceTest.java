package com.airesdev.url_shortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.model.Url;
import com.airesdev.url_shortener.repository.UrlRepository;
import com.airesdev.url_shortener.service.UrlService;

public class UrlServiceTest {
    
    private UrlRepository repository;
    private UrlService service;

    @BeforeEach
    void setUp() {
        repository = mock(UrlRepository.class);
        service = new UrlService(repository);
    }

    @Test
    void testSave_ShouldReturnSavedUrl() {

        String originalUrl = "https://google.com";
        UrlDTO dto = new UrlDTO(originalUrl);

        ArgumentCaptor<Url> captor = ArgumentCaptor.forClass(Url.class);
        when(repository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Url saved = service.save(dto);

        verify(repository).save(captor.capture());
        Url captured = captor.getValue();

        assertNotNull(saved);
        assertEquals(originalUrl, saved.getOriginalUrl());
        assertEquals(saved.getOriginalUrl(), captured.getOriginalUrl());
        assertNotNull(saved.getShortUrl());
        assertEquals(9, saved.getShortUrl().length());
        assertTrue(saved.getShortUrl().matches("[a-zA-Z0-9]+"));
    }

    @Test
    void testFindByShortUrl_ShouldReturnUrl_WhenFound() {

        String shortUrl = "abc12345";
        Url mockUrl = new Url(UUID.randomUUID(), "https://example.com", shortUrl);
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        Url result = service.findByShortUrl(shortUrl);

        assertNotNull(result);
        assertEquals(mockUrl.getShortUrl(), result.getShortUrl());
        assertEquals(mockUrl.getOriginalUrl(), result.getOriginalUrl());
    }

    @Test
    void testFindByShortUrl_ShouldThrowException_WhenNotFound() {

        String shortUrl = "notfound";
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.findByShortUrl(shortUrl);
        });

        assertEquals("Short URL não encontrada", exception.getMessage());
    }
}
