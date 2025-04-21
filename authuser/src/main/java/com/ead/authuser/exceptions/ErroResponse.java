package com.ead.authuser.exceptions;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponse(
        int errorCode,
        String errorMessage,
        Map<String, String> errorsDetails) {
}
