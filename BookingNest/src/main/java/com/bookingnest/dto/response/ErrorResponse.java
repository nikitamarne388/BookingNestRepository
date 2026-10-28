package com.bookingnest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ErrorResponse {

    private int statusCode;
    private String statusMessage;
    private LocalDateTime timestamp;
    private String api;

    public ErrorResponse(int statusCode, String statusMessage, LocalDateTime timestamp, String api) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.timestamp = timestamp;
        this.api = api;
    }
}
