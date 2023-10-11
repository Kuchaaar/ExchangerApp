package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.domain.exceptions.NoDataException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExcelMaker {
    private final CurrencyRepository currencyRepository;
    private final ExcelGenerator excelGenerator;

    public ExcelMaker(CurrencyRepository currencyRepository, ExcelGenerator excelGenerator){
        this.currencyRepository = currencyRepository;
        this.excelGenerator = excelGenerator;
    }

    public byte[] generateExcel(ReportPeriod reportPeriod) throws IOException, NoDataException{
            return excelGenerator.generateExcelWorkbook(new CurrencyReportDatasource(
                    currencyRepository
                            .findByDates(reportPeriod.startDate(), reportPeriod.endDate())
                            .stream()
                            .map(CurrencyReportCurrency::from)
                            .toList()
            ),false);
    }

    public byte[] generateExcelByCurrency(CurrencyReportPeriod currencyReportPeriod, boolean isExtension)
            throws IOException, NoDataException{
        return excelGenerator.generateExcelWorkbook(new CurrencyReportDatasource(
                currencyRepository
                        .findCurrencyByDates(
                                currencyReportPeriod.reportPeriod().startDate(),
                                currencyReportPeriod.reportPeriod().endDate(),
                                currencyReportPeriod.currencyCode()
                        )
                        .stream()
                        .map(CurrencyReportCurrency::from)
                        .toList()
        ),isExtension);
    }
}
