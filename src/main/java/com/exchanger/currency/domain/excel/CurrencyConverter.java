package com.exchanger.currency.domain.excel;


import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class CurrencyConverter {
    private final DateConverter dateConverter;
    private final DataIntoMap dataIntoMap;

    public CurrencyConverter(DateConverter dateConverter, DataIntoMap dataIntoMap) {
        this.dateConverter = dateConverter;
        this.dataIntoMap = dataIntoMap;
    }

    public CurrencyReportPeriod currencyConvertion(String data) {
        Map<String, String> paramMap = dataIntoMap.dataIntoMap(data);
        ReportPeriod reportPeriod = dateConverter.stringIntoReportPeriod(data);
        String currencyCode = paramMap.get("currency");
        return new CurrencyReportPeriod(reportPeriod, currencyCode);
    }

}
