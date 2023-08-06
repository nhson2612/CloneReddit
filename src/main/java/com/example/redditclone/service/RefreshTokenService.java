package com.example.redditclone.service;

import com.example.redditclone.model.RefreshToken;
import com.example.redditclone.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreateDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void validRefreshToken(String token){
        refreshTokenRepository.findByToken(token).get();
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }


}
