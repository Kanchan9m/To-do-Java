package com.example.todos.middleware;

import com.example.todos.security.Jwtservices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;

import java.io.IOException;

@Component
public class JwtFilter implements Filter{

    @Autowired
    private Jwtservices jwtservices;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if(path.contains("/auth/login") || path.contains("/auth/register")){
            chain.doFilter(request, response);
            return;
        }
        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            try{
                Long userId = jwtservices.extractUserId(token);
                req.setAttribute("userId", userId);
                chain.doFilter(request, response);
            }
            catch (Exception e){
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        }else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid header");
        }
    }

}
