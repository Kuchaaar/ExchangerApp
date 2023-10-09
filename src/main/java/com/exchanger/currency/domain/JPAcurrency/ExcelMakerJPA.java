package com.exchanger.currency.domain.JPAcurrency;

import com.exchanger.currency.domain.excel.ReportPeriod;
import com.exchanger.currency.exceptions.NoDataException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class ExcelMakerJPA {
    private final CurrencyRepositoryJPA currencyRepositoryJPA;
    private final ExcelGeneratorJPA excelGeneratorJPA;

    public ExcelMakerJPA(CurrencyRepositoryJPA currencyRepositoryJPA, ExcelGeneratorJPA excelGeneratorJPA) {
        this.currencyRepositoryJPA = currencyRepositoryJPA;
        this.excelGeneratorJPA = excelGeneratorJPA;
    }

    public byte[] generateExcelByCurrency(ReportPeriod reportPeriod)
            throws IOException, NoDataException {
        List<CurrencyJPA> currencyResponseList =
                currencyRepositoryJPA.findEntitiesBetweenDates(
                        reportPeriod.startDate(),
                        reportPeriod.endDate());
        if(currencyResponseList.isEmpty()){
            throw new NoDataException();
        }
        else{
            return excelGeneratorJPA.generateExcelWorkbook(currencyResponseList);
        }

    }

}
