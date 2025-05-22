package com.airesdev.url_shortener.docs;

import org.springframework.http.ResponseEntity;

import com.airesdev.url_shortener.dto.UrlDTO;
import com.airesdev.url_shortener.model.Url;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UrlControllerDocs {
    
    @Operation(summary = "Cria uma URL encurtada a partir da URL original")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "URL encurtada criada com sucesso",
            content = @Content(schema = @Schema(implementation = Url.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    ResponseEntity<Url> createShortUrl(
            @RequestBody UrlDTO urlDto
    );

    @Operation(summary = "Redireciona para a URL original a partir da short URL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Redirecionamento para a URL original",
            headers = @Header(name = "Location", description = "URL original")),
        @ApiResponse(responseCode = "404", description = "Short URL não encontrada", content = @Content)
    })
    ResponseEntity<Void> getOriginalUrl(
        @Parameter(description = "Identificador da short URL") String shortUrl
    );
}
