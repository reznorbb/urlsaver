package com.rezi.recruitment.urlsaver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezi.recruitment.urlsaver.dto.UrlDto;
import com.rezi.recruitment.urlsaver.service.document.DocumentSaverService;
import java.util.Map;
import static org.hamcrest.MatcherAssert.*;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    public static final String VALID_URL = "http://testurl.pl";
    public static final String INVALID_URL = "invalidurl";
    private static final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    DocumentSaverService<UrlDto> documentSaverService;
    @Autowired
    private MockMvc mockMvc;


    @Nested
    @DisplayName("Testing '/documents/queue' requests")
    class TestsForDocumentsQueue {

        @Test
        void givenValidUrl_whenPerformingPostRequest_thenReturnAcceptedResponse() throws Exception {
            // given
            ArgumentCaptor<UrlDto> urlDtoCaptor = ArgumentCaptor.forClass(UrlDto.class);
            String json = mapper.writeValueAsString(Map.of("url", VALID_URL));
            RequestBuilder request = MockMvcRequestBuilders
                    .post("/documents/queue")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // when + then
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isAccepted())
                    .andExpect(jsonPath("$.message", Matchers.equalTo(DocumentController.ACCEPTANCE_TEXT)));

            BDDMockito.then(documentSaverService).should().save(urlDtoCaptor.capture());

            var urlDtoCaptured = urlDtoCaptor.getValue();
            assertThat(urlDtoCaptured.url(), is(VALID_URL));
        }

        @Test
        void givenInvalidUrl_whenPerformingPostRequest_thenReturnBadRequest() throws Exception {
            // given
            ArgumentCaptor<UrlDto> urlDtoCaptor = ArgumentCaptor.forClass(UrlDto.class);
            String json = mapper.writeValueAsString(Map.of("url", INVALID_URL));
            RequestBuilder request = MockMvcRequestBuilders
                    .post("/documents/queue")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // when + then
            mockMvc.perform(request)
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}