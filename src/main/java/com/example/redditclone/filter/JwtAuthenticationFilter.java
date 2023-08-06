package com.example.redditclone.filter;

import com.example.redditclone.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String username = null;
        if(authHeader==null||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            username = jwtService.getUsername(jwt);
        }catch(ExpiredJwtException e){
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request,response);
            return;
        }


//        if(username!=null&&SecurityContextHolder.getContext().getAuthentication()!=null){
//            if(jwtService.isTokenExpired(jwt)){
//                SecurityContextHolder.clearContext();
//                filterChain.doFilter(request,response);
//                return;
//            }
//        }
//        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if(jwtService.validJwtToken(jwt,user)){
                UsernamePasswordAuthenticationToken upAuthToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(upAuthToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/auth/**");
    }
}