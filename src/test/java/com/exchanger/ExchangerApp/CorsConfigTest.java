package com.exchanger.ExchangerApp;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsConfigTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCorsConfiguration() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setOrigin("http://google.com/");
        headers.set(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET.name());

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.OPTIONS, new URI("http://localhost:8080/available_codes"));

        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);

        HttpHeaders responseHeaders = responseEntity.getHeaders();

        System.out.println("Access-Control-Allow-Origin: " + responseHeaders.getAccessControlAllowOrigin());
        System.out.println("Access-Control-Allow-Methods: " + responseHeaders.getAccessControlAllowMethods());
        System.out.println("Access-Control-Allow-Headers: " + responseHeaders.getAccessControlAllowHeaders());
        System.out.println("Access-Control-Allow-Credentials: " + responseHeaders.getAccessControlAllowCredentials());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("http://google.com/", responseHeaders.getAccessControlAllowOrigin());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE"), responseHeaders.getAccessControlAllowMethods());
        assertEquals(List.of("Content-Type"), responseHeaders.getAccessControlAllowHeaders());
        assertEquals(true, responseHeaders.getAccessControlAllowCredentials());
    }
}