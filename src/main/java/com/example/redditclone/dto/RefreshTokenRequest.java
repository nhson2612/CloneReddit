package com.example.redditclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;
    private String username;

}
