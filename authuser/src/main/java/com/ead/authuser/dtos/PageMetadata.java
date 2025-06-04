package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageMetadata {

    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final int number;

    @JsonCreator
    public PageMetadata(@JsonProperty("size") int size, @JsonProperty("totalElements") long totalElements,
            @JsonProperty("totalPages") int totalPages, @JsonProperty("number") int number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

}
