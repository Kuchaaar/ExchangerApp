package com.exchanger.currency.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@ConfigurationProperties(prefix = "exchanger")
public record CorsProperties(
        List<String> allowedApi,
        List<String> allowedMethods,
        List<String> allowedHeaders,
        boolean allowCredentials,
        List<String> exposedHeader,
        Long maxAge
) {
}