package com.exchanger.currency.excel;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class DataIntoMap {
    public Map<String,String> dataIntoMap(String data) {
        Map<String, String> paramMap = new HashMap<>();
        String[] params = data.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                paramMap.put(keyValue[0], keyValue[1]);
            }
        }
        return paramMap;
    }
}
