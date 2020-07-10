package com.search.text.utils.search;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Scanner {

    public int findTextOccurrencesCount(final Document document, final String text) {
        String htmlAsString = document.baseUri() + " " + document.text();

        return StringUtils.countOccurrencesOf(htmlAsString, text);

    }

    public List<String> findLinks(final Document document) {
        Elements elements = document.select("a[href]");

        return elements.stream()
                .map(element -> element.attr("href"))
                .distinct()
                .filter(e -> e.startsWith("http") || e.startsWith("https"))
                .collect(Collectors.toList());
    }
}
