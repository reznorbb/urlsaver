package com.rezi.recruitment.urlsaver.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
class DocumentMetadataRepositoryTest {

    @Autowired
    DocumentMetadataRepository documentMetadataRepository;

    @Test
    void repositoryNotNull() {
        assertNotNull(documentMetadataRepository);
    }

    @Test
    @Sql("/data/createDocumentMetadata.sql")
    void findById() {
        // given + when
        var documentMetadata = documentMetadataRepository.findById(2L);

        // then
        assertThat(documentMetadata.isPresent(), is(true));
    }

}