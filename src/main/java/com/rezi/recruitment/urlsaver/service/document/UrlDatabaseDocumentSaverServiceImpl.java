package com.rezi.recruitment.urlsaver.service.document;

import com.rezi.recruitment.urlsaver.dao.DocumentContentRepository;
import com.rezi.recruitment.urlsaver.dao.DocumentMetadataRepository;
import com.rezi.recruitment.urlsaver.dto.UrlDto;
import com.rezi.recruitment.urlsaver.service.stream.StreamProviderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Saves the document of type 'UrlDto' in database.
 * Saving is asynchronous and backed with a queue provided by @Async annotation.
 *
 */
@Service
@Transactional
class UrlDatabaseDocumentSaverServiceImpl extends AbstractDatabaseDocumentSaverService<UrlDto> {

    public UrlDatabaseDocumentSaverServiceImpl(DocumentMetadataRepository documentMetadataRepository, DocumentContentRepository documentContentRepository, StreamProviderService<UrlDto> streamProvider) {
        super(documentMetadataRepository, documentContentRepository, streamProvider);
    }

    @Async("urlDatabaseDocumentSaverExecutor")
    @Override
    public void save(UrlDto source) {
        super.save(source);
    }

    @Override
    protected String getSourceIdentity(UrlDto source) {
        return source.url();
    }
}
