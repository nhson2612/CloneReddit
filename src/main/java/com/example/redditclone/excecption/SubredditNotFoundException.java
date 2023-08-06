package com.example.redditclone.excecption;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String messsage){
        super(messsage);
    }
}
