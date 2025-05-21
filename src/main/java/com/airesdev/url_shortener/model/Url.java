package com.airesdev.url_shortener.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "urls")
public class Url {
    
    @Id
    private UUID id;

    private String originalUrl;

    @Indexed(unique = true)
    private String shortUrl;
}
