package com.example.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private Instant time;
    private HttpStatus status;

}
