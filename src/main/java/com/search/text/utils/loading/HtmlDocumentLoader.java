package com.search.text.utils.loading;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlDocumentLoader implements DocumentLoader {

    @Override
    public Document download(final String path) {
        try {
            return Jsoup.connect(path).get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
