package com.search.text.bfs;

import com.search.text.model.SearchParameters;
import com.search.text.utils.loading.HtmlDocumentLoader;
import com.search.text.utils.search.Scanner;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ConcurrentBreadthFirstSearch {

    private final List<String> links = new CopyOnWriteArrayList<>();

    private final Scanner scanner;
    private final HtmlDocumentLoader documentLoader;

    @Autowired
    public ConcurrentBreadthFirstSearch(final Scanner scanner, final HtmlDocumentLoader documentLoader) {
        this.scanner = scanner;
        this.documentLoader = documentLoader;
    }

    public List<Document> breadthFirstSearchDocuments(final SearchParameters parameters) {
        ExecutorService service = Executors.newFixedThreadPool(parameters.getQuantityThreads());

        List<Document> documents = new CopyOnWriteArrayList<>();
        AtomicInteger cursor = new AtomicInteger(0);
        AtomicReference<Document> document = new AtomicReference<>();

        links.add(parameters.getRootUrl());

        try {
            service.submit(() -> {
                while (cursor.get() < parameters.getQuantityUrlToScan()) {
                    document.set(documentLoader.download(links.get(cursor.get())));
                    List<String> scanLinks = scanner.findLinks(document.get());
                    insertLinksWithOutDuplicate(scanLinks);

                    documents.add(document.get());

                    cursor.incrementAndGet();
                }
            });
        } finally {
            try {
                service.shutdown();
                service.awaitTermination(30, TimeUnit.SECONDS);
                links.removeAll(links);
                cursor.set(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            return documents;
        }
    }

    private void insertLinksWithOutDuplicate(final List<String> insertLinks) {
        if (insertLinks.size() > 0) {

            for (String link : insertLinks) {
                if (!links.contains(link)) {
                    links.add(link);
                }
            }
        }
    }
}
