package com.search.text.services;

import com.search.text.model.SearchParameters;

import java.util.Map;

public interface DocumentService {

    public Map<String, Integer> searchText(SearchParameters parameters);
}
