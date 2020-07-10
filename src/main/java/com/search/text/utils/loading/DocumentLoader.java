package com.search.text.utils.loading;


import org.jsoup.nodes.Document;

import java.io.IOException;

public interface DocumentLoader {

    public Document download(String path) throws IOException;
}
