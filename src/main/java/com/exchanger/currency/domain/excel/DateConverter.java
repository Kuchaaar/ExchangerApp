package com.exchanger.currency.domain.excel;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class DateConverter {
    public final DataIntoMap dataIntoMap;

    public DateConverter(DataIntoMap dataIntoMap) {
        this.dataIntoMap = dataIntoMap;
    }

    public ReportPeriod stringIntoReportPeriod(String dates){
        Map<String, String> paramMap = dataIntoMap.dataIntoMap(dates);
        java.time.LocalDate data1 = java.time.LocalDate.parse(paramMap.get("data1"));
        java.time.LocalDate data2 = java.time.LocalDate.parse(paramMap.get("data2"));
        return new ReportPeriod(data1,data2);
    }
}
