package com.search.text.controller;

import com.search.text.model.SearchParameters;
import com.search.text.services.DocumentService;
import com.search.text.services.HtmlDocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HtmlDocumentController {
    private static final String PATH_DOCUMENT = "/search";

    private final DocumentService documentServices;

    @Autowired
    public HtmlDocumentController(final HtmlDocumentServiceImpl documentServices) {
        this.documentServices = documentServices;
    }

    @PostMapping(PATH_DOCUMENT)
    public ResponseEntity<Map<String, Integer>> search(@RequestBody final SearchParameters parameters) {
        Map<String, Integer> map = documentServices.searchText(parameters);
        return ResponseEntity.ok(map);
    }
}
