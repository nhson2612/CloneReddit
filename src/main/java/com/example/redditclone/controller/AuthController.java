package com.example.redditclone.controller;

import com.example.redditclone.dto.AuthenticationResponse;
import com.example.redditclone.dto.LoginRequest;
import com.example.redditclone.dto.RefreshTokenRequest;
import com.example.redditclone.dto.RegisterRequest;
import com.example.redditclone.service.AuthService;
import com.example.redditclone.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verificationAccount(@PathVariable("token") String token){
        authService.verificationToken(token);
        return new ResponseEntity<>("Your account activated !",HttpStatus.OK);
    }

    @PostMapping("/login")

    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        AuthenticationResponse response =  authService.login(loginRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
