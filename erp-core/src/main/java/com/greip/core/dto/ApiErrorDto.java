package com.greip.core.dto;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HvivesO on 06/01/2017.
 */
public class ApiErrorDto {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorDto(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
