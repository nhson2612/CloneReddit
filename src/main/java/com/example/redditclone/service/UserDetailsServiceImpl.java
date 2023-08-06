package com.example.redditclone.service;

import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("Not found user");
        }else {
            User user1 = userOptional.get();
            org.springframework.security.core.userdetails.User user = new
                    org.springframework.security.core.userdetails.User(user1.getUsername(),user1.getPassword(),true,true,true,true,getAuthorities("USER"));
            return user;
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String authorities) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorities);
        return List.of(authority);
    }
}
