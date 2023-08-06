package com.example.redditclone.service;

import com.example.redditclone.dto.AuthenticationResponse;
import com.example.redditclone.dto.LoginRequest;
import com.example.redditclone.dto.RefreshTokenRequest;
import com.example.redditclone.dto.RegisterRequest;
import com.example.redditclone.model.NotificationEmail;
import com.example.redditclone.model.User;
import com.example.redditclone.model.VerificationToken;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);

        mailService.send(new NotificationEmail("Pls active your account",user.getEmail(),"Click on this url to active your account" +
                " http://localhost:8080/api/auth/accountVerification/"+token));
    }

    public void verificationToken(String token){
        Optional<VerificationToken> verificationTokenOptional =  verificationTokenRepository.findByToken(token);
        if(verificationTokenOptional.isEmpty()){

        }else {
            VerificationToken verificationToken = verificationTokenOptional.get();
            Optional<User> userOptional = userRepository.findByUsername(verificationToken.getUser().getUsername());
            if(userOptional.isEmpty()){
                throw new UsernameNotFoundException("Not found any user with username = " + verificationToken.getUser().getUsername());
            }else{
                User user = userOptional.get();
                user.setEnabled(true);
                userRepository.save(user);
            }

        }
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        Optional<User> userOptional =userRepository.findByUsername(loginRequest.getUsername());
        if(userOptional.isEmpty()){
            throw new NoSuchElementException();
        }else{
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

            Collection<GrantedAuthority> authorities = List.of(authority);

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    loginRequest.getUsername() , loginRequest.getPassword(), authorities
            );
            UsernamePasswordAuthenticationToken upAuthenticationToken = new UsernamePasswordAuthenticationToken(
                   user,loginRequest.getPassword(),authorities
            );
            SecurityContextHolder.getContext().setAuthentication(upAuthenticationToken);

            String jwt = jwtService.generateJwtToken(user);
            return AuthenticationResponse.builder()
                    .jwt(jwt)
                    .username(loginRequest.getUsername())
                    .exp(Instant.now().plusMillis(100*60*60*24))
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .build();
        }

    }

    public User getCurrentUser(){
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       User currentUser = userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User name not found " + user.getUsername()));
        return currentUser;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validRefreshToken(refreshTokenRequest.getRefreshToken());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.
                User(refreshTokenRequest.getUsername(),null ,List.of(authority));
        String jwt = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .exp(Instant.now().plusMillis(1000*60*60*24))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
