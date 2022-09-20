package com.rezi.recruitment.urlsaver.service.stream;

import com.rezi.recruitment.urlsaver.dto.UrlDto;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class UrlStreamProviderServiceImpl implements StreamProviderService<UrlDto> {

    private static final Logger logger = LoggerFactory.getLogger(UrlStreamProviderServiceImpl.class);

    @Override
    public InputStream provideStream(UrlDto urlDto) {
        InputStream inputStream = null;
        try {
            inputStream = new URL(urlDto.url()).openStream();
        } catch (IOException e) {
            logger.debug("Could not obtain stream for " + urlDto.url(), e);
        }
        return inputStream;
    }
}
