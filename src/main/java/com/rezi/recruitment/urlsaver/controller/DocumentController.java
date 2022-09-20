package com.rezi.recruitment.urlsaver.controller;

import com.rezi.recruitment.urlsaver.dto.UrlDto;
import com.rezi.recruitment.urlsaver.service.document.DocumentSaverService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
    public static final String ACCEPTANCE_TEXT = "Your Request Has Been Accepted.";
    final DocumentSaverService<UrlDto> documentSaverService;

    public DocumentController(DocumentSaverService<UrlDto> documentSaverService) {
        this.documentSaverService = documentSaverService;
    }

    @RequestMapping("/queue")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Object> saveDocument(@Valid @RequestBody UrlDto url) {
        documentSaverService.save(url);
        return ResponseEntity.accepted().body(Map.of("message", ACCEPTANCE_TEXT));
    }
}
