package com.exchanger.currency.excel;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Component
public class ExcelMaker {
    private final CurrencyRepository currencyRepository;
    private final ExcelGenerator excelGenerator;

    public ExcelMaker(CurrencyRepository currencyRepository, ExcelGenerator excelGenerator) {
        this.currencyRepository = currencyRepository;
        this.excelGenerator = excelGenerator;
    }
    public byte[] generateExcel(ReportPeriod reportPeriod) throws IOException {
        List<Currency> currencyResponseList =
                currencyRepository.findByDates(reportPeriod.startDate(),reportPeriod.endDate());
        if(currencyResponseList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else{
            return excelGenerator.generateExcelWorkbook(currencyResponseList);
        }
    }
}
