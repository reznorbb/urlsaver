package com.rezi.recruitment.urlsaver.service.stream;

import com.rezi.recruitment.urlsaver.dto.UrlDto;
import java.io.InputStream;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.BDDMockito;

class UrlStreamProviderServiceImplTest {

    public static final String VALID_BUT_NON_EXISTING_URL = "http://testurl.pl";
    public static final String INVALID_URL = "invalidurl";

    UrlStreamProviderServiceImpl SUT = new UrlStreamProviderServiceImpl();

    @Test
    void givenInvalidUrl_whenProvidingStream_thenProvideNull() {
        // given
        UrlDto urlDto = new UrlDto(INVALID_URL);

        // when
        InputStream result = SUT.provideStream(urlDto);

        // then
        assertThat(result, is(nullValue()));
    }

    @Test
    void givenValidUrlSyntaxButNonExistentUrl_whenProvidingStream_thenProvideNull() {
        // given
        UrlDto urlDto = new UrlDto(VALID_BUT_NON_EXISTING_URL);

        // when
        InputStream result = SUT.provideStream(urlDto);

        // then
        assertThat(result, is(nullValue()));
    }

}