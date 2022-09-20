package com.rezi.recruitment.urlsaver.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
class DocumentContentRepositoryTest {

    @Autowired
    DocumentContentRepository documentContentRepository;

    @Test
    void repositoryNotNull() {
        assertNotNull(documentContentRepository);
    }

}