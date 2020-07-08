package com.search.text.utils.links;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListLinks {
    private List<String> listLinks = new LinkedList<>();

    public int insertOneLink(final String path) {
        listLinks.add(path);
        return listLinks.size();
    }

    public synchronized int insertAllLinks(final List<String> links) {
        listLinks.addAll(links);

        listLinks = listLinks.stream().distinct().collect(Collectors.toList());

        return listLinks.size();
    }

    public synchronized String getLink(int index) {
        return listLinks.get(index);
    }

    public void removeAll() {
        listLinks = new LinkedList<>();
    }
}
