package com.exchanger.currency.excel;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DateConverter {
    public List<LocalDate> stringIntoLocalDates(String dates){
        Map<String, String> paramMap = new HashMap<>();
        String[] params = dates.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                paramMap.put(keyValue[0], keyValue[1]);
            }
        }
        java.time.LocalDate data1 = java.time.LocalDate.parse(paramMap.get("data1"));
        java.time.LocalDate data2 = java.time.LocalDate.parse(paramMap.get("data2"));
        return List.of(data1,data2);
    }
}
