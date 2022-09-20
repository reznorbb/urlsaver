package com.rezi.recruitment.urlsaver.service.stream;

import java.io.InputStream;
@FunctionalInterface
public interface StreamProviderService<T> {

    /**
     * Provides a stream for a given source
     *
     * @param source
     * @return InputStream or null if the stream can't be obtained. Stream must be open.
     */
    InputStream provideStream(T source);
}
