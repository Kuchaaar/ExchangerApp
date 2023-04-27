package com.API;

import org.springframework.cloud.openfeign.FeignClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.List;

@FeignClient(url = "${feign.url}")
public class APIMain {

    public String url = "http://api.nbp.pl/api/exchangerates/tables/";
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public void showAll() {
        CurrencyClient apiHolder = retrofit().create(CurrencyClient.class);
        List<Currency> response = (List<Currency>) apiHolder.getByTable("A");
        response.stream().forEach(System.out::println);
    }
}
