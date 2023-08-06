package com.example.redditclone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String subredditName;
    @NotNull(message = "post name cannot be null")
    private String postName;
    private String url;
    private String description;


}
