package com.rezi.recruitment.urlsaver.service.document;

import com.rezi.recruitment.urlsaver.dao.DocumentContent;
import com.rezi.recruitment.urlsaver.dao.DocumentContentRepository;
import com.rezi.recruitment.urlsaver.dao.DocumentMetadata;
import com.rezi.recruitment.urlsaver.dao.DocumentMetadataRepository;
import com.rezi.recruitment.urlsaver.service.stream.StreamProviderService;
import java.io.InputStream;
import java.time.LocalDateTime;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Saves the document of type T in database.
 * Binary content of the document is saved in DocumentContent repository and the content is provided by 'StreamProviderService'.
 * Any additional information regarding the document will be saved in 'DocumentMetadata' repository.
 *
 * Type Parameters:
 * T - the type of document to save
 *
 * @param <T>
 */
abstract class AbstractDatabaseDocumentSaverService<T> implements DocumentSaverService<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected DocumentMetadataRepository documentMetadataRepository;
    protected DocumentContentRepository documentContentRepository;
    protected StreamProviderService<T> streamProvider;

    @Value("${document.save.maximum-size.bytes}")
    protected long maxDocumentSizeBytes;

    protected AbstractDatabaseDocumentSaverService(DocumentMetadataRepository documentMetadataRepository, DocumentContentRepository documentContentRepository, StreamProviderService<T> streamProvider) {
        this.documentMetadataRepository = documentMetadataRepository;
        this.documentContentRepository = documentContentRepository;
        this.streamProvider = streamProvider;
    }

    @Override
    public void save(T source) {
        InputStream documentStream = streamProvider.provideStream(source);
        if (documentStream != null) {
            saveInDb(source, documentStream);
            logger.info("Finished saving document: {}", source);
        } else {
            recordFailedAttempt(source);
        }
    }

    protected void saveInDb(T source, InputStream documentStream) {
        DocumentMetadata documentMetadata = new DocumentMetadata();
        documentMetadata.setSource(getSourceIdentity(source));
        documentMetadata.setCreated(LocalDateTime.now());
        documentMetadataRepository.save(documentMetadata);

        DocumentContent content = new DocumentContent();
        content.setDocumentMetadata(documentMetadata);
        content.setData(BlobProxy.generateProxy(documentStream, maxDocumentSizeBytes));
        documentContentRepository.saveAndFlush(content);
    }

    protected void recordFailedAttempt(T source) {
        logger.info("Couldn't save document: {}", source);
    }

    protected String getSourceIdentity(T source) {
        // Returns a name under which the document will be saved in database
        return source.toString();
    }
}
