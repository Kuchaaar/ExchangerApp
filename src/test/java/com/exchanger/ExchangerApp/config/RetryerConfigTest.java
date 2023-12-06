package com.exchanger.ExchangerApp.config;

import com.exchanger.currency.integration.holidays.HolidaysClient;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.springtest.MockServerTest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ApplicationContext;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(classes = {HolidaysClient.class})
@EnableFeignClients(clients = HolidaysClient.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@MockServerTest({"spring.cloud.openfeign.client.config.holidays.url=http://localhost:${mockServerPort}"})
class RetryerConfigTest{

    @SpyBean
    private HolidaysClient holidaysClient;
    private MockServerClient mockServerClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRetryerAttempts(){
        applicationContext.getBeanDefinitionCount();
        HttpRequest requestDefinition = request().withMethod("GET").withPath("/2023/PL");
        mockServerClient
                .when(requestDefinition)
                .respond(response()
                        .withHeader("Retry-After", "1")
                        .withStatusCode(HttpStatusCode.SERVICE_UNAVAILABLE_503.code())
                        .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON));
        try{
            holidaysClient.getHolidays(2023);
        }catch(final Exception e){
            System.out.println(e);
        }finally{
            mockServerClient.verify(requestDefinition, VerificationTimes.exactly(5));
        }
    }
}