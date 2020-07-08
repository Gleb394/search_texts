package com.search.text.services;

import com.search.text.model.SearchParameters;
import com.search.text.utils.links.ListLinks;
import com.search.text.utils.loading.LoadingHtmlDocuments;
import com.search.text.utils.search.Scanner;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ImplHtmlDocumentServices implements DocumentServices {
    private final Scanner scanner;
    private final ListLinks listLinks;
    private final LoadingHtmlDocuments loading;

    private volatile String url;
    private volatile int currentSize;
    private volatile int number;

    @Autowired
    public ImplHtmlDocumentServices(Scanner scanner, ListLinks links, LoadingHtmlDocuments loading) {
        this.scanner = scanner;
        this.listLinks = links;
        this.loading = loading;

    }

    public Map<String, Integer> searchText(final SearchParameters parameters) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(parameters.getQuantityThreads());

        listLinks.insertOneLink(parameters.getRootUrl());

        Map<String, Integer> result = new ConcurrentHashMap<>();
        AtomicInteger cursor = new AtomicInteger(0);

        try {
            service.submit(() -> {
                while (cursor.get() <= parameters.getQuantityUrlToScan()) {
                    Document document = getDocument(cursor.get());

                    if (currentSize < parameters.getQuantityUrlToScan()) {
                        currentSize = setLinks(document);
                    }

                    number = scanner.scanMatchesText(document, parameters.getText());
                    result.put(url, number);

                    cursor.incrementAndGet();
                }
            });

        } finally {
            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
        }

        currentSize = 0;
        listLinks.removeAll();

        return result;
    }

    private Document getDocument(final int cursor) {
        url = listLinks.getLink(cursor);
        return loading.download(url);
    }

    private int setLinks(final Document document) {
        List<String> links = scanner.findLinks(document);
        return this.listLinks.insertAllLinks(links);
    }
}
