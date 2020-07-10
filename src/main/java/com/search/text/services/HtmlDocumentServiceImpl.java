package com.search.text.services;

import com.search.text.bfs.ConcurrentBreadthFirstSearch;
import com.search.text.model.SearchParameters;
import com.search.text.utils.search.Scanner;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HtmlDocumentServiceImpl implements DocumentService {
    private final Scanner scanner;
    private final ConcurrentBreadthFirstSearch bfs;

    @Autowired
    public HtmlDocumentServiceImpl(final Scanner scanner, ConcurrentBreadthFirstSearch bfs) {
        this.scanner = scanner;
        this.bfs = bfs;
    }

    @Override
    public Map<String, Integer> searchText(final SearchParameters parameters) {
        List<Document> documents = bfs.breadthFirstSearchDocuments(parameters);

        return null;
    }
}
