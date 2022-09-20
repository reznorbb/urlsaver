package com.rezi.recruitment.urlsaver.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UrlDto(@NotBlank @Size(max = 1024) @URL String url) {
}
