package com.example.redditclone.excecption;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message){
        super(message);
    }
}
