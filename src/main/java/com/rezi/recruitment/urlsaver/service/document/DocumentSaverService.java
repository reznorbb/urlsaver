package com.rezi.recruitment.urlsaver.service.document;

public interface DocumentSaverService<T> {

    /**
     * Type Parameters:
     * T - the type of document to save
     *
     * @param document - document to save
     */
    void save(T document);
}
