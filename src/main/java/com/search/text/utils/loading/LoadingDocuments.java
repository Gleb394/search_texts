package com.search.text.utils.loading;

import java.io.IOException;

public interface LoadingDocuments<T> {

    public T download(String path) throws IOException;
}
