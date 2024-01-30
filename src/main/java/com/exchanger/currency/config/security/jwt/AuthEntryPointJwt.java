package com.exchanger.currency.config.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    private final HandlerExceptionResolver handlerExceptionResolver;

    public AuthEntryPointJwt(HandlerExceptionResolver handlerExceptionResolver){
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException{
        logger.error("Unauthorized error: {}", authException.getMessage());
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}