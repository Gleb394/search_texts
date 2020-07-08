package com.search.text.model;

public class SearchParameters {

    private String rootUrl;
    private String text;
    private int quantityUrlToScan;
    private int quantityThreads;

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuantityUrlToScan() {
        return quantityUrlToScan;
    }

    public void setQuantityUrlToScan(int quantityUrlToScan) {
        this.quantityUrlToScan = quantityUrlToScan;
    }

    public int getQuantityThreads() {
        return quantityThreads;
    }

    public void setQuantityThreads(int quantityThreads) {
        this.quantityThreads = quantityThreads;
    }
}
