package com.exchanger.ExchangerApp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsConfigTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCorsConfiguration() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setOrigin("http://localhost");
        headers.set(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET.name());
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.OPTIONS, new URI("http://localhost:8080/available_codes"));
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
        System.out.println(responseEntity);

        HttpHeaders responseHeaders = responseEntity.getHeaders();

        System.out.println("Access-Control-Allow-Origin: " + responseHeaders.getAccessControlAllowOrigin());
        System.out.println("Access-Control-Allow-Methods: " + responseHeaders.getAccessControlAllowMethods());
        System.out.println("Access-Control-Allow-Headers: " + responseHeaders.getAccessControlAllowHeaders());
        System.out.println("Access-Control-Allow-Credentials: " + responseHeaders.getAccessControlAllowCredentials());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("http://localhost", responseHeaders.getAccessControlAllowOrigin());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE"), responseHeaders.getAccessControlAllowMethods());
        assertEquals(List.of("Content-Type"), responseHeaders.getAccessControlAllowHeaders());
        assertEquals(true, responseHeaders.getAccessControlAllowCredentials());
    }
}