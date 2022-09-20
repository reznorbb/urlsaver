package com.rezi.recruitment.urlsaver.service.document;

import com.rezi.recruitment.urlsaver.dao.DocumentContentRepository;
import com.rezi.recruitment.urlsaver.dao.DocumentMetadataRepository;
import com.rezi.recruitment.urlsaver.dto.UrlDto;
import com.rezi.recruitment.urlsaver.service.stream.StreamProviderService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlDatabaseDocumentSaverServiceImplTest {

    @Mock
    DocumentMetadataRepository documentMetadataRepository;

    @Mock
    DocumentContentRepository documentContentRepository;

    @Mock
    StreamProviderService<UrlDto> streamProvider;

    @InjectMocks
    UrlDatabaseDocumentSaverServiceImpl SUT;


    @Test
    void givenNullStreamProvided_whenSavingDocument_thenNoInteractionsWithDatabase() {
        // given
        UrlDto urlDto = new UrlDto("http://testurl.pl");
        BDDMockito.given(streamProvider.provideStream(urlDto)).willReturn(null);

        // when
        SUT.save(urlDto);

        // then
        BDDMockito.then(documentMetadataRepository).shouldHaveNoInteractions();
        BDDMockito.then(documentContentRepository).shouldHaveNoInteractions();
    }

    @Test
    void givenStreamProvided_whenSavingDocument_thenSaveInDatabase() {
        // given
        UrlDto urlDto = new UrlDto("http://testurl.pl");
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());
        BDDMockito.given(streamProvider.provideStream(urlDto)).willReturn(inputStream);

        // when
        SUT.save(urlDto);

        // then
        BDDMockito.then(documentMetadataRepository).should().save(argThat(document -> document.getSource().equals(urlDto.url())));
        BDDMockito.then(documentContentRepository).should().saveAndFlush(argThat(document -> document.getDocumentMetadata().getSource().equals(urlDto.url())));
        BDDMockito.then(documentContentRepository).should().saveAndFlush(argThat(document -> {
            try {
                return document.getData().getBinaryStream() == inputStream;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}