package com.search.text.utils.loading;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoadingHtmlDocuments implements LoadingDocuments<Document> {

    @Override
    public Document download(final String path) {
        Document document = null;

        try {
            document = Jsoup.connect(path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }
}
