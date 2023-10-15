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

    public byte[] excelFileFromReportPeriod(ReportPeriod reportPeriod) throws IOException, NoDataException{
            return excelGenerator.excelFileToByteArray(new CurrencyReportDatasource(
                    currencyRepository
                            .findByDates(reportPeriod.startDate(), reportPeriod.endDate())
                            .stream()
                            .map(CurrencyReportCurrencies::from)
                            .toList()
            ),false);
    }

    public byte[] excelFileFromCurrencyReportPeriod(CurrencyReportPeriod currencyReportPeriod, boolean isExtension)
            throws IOException, NoDataException{
        return excelGenerator.excelFileToByteArray(new CurrencyReportDatasource(
                currencyRepository
                        .findCurrencyByDates(
                                currencyReportPeriod.reportPeriod().startDate(),
                                currencyReportPeriod.reportPeriod().endDate(),
                                currencyReportPeriod.currencyCode()
                        )
                        .stream()
                        .map(CurrencyReportCurrencies::from)
                        .toList()
        ),isExtension);
    }
}
