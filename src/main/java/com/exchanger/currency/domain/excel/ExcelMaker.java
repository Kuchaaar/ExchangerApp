package com.exchanger.currency.domain.excel;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.exceptions.NoDataException;
import org.springframework.stereotype.Component;

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
    public byte[] generateExcel(ReportPeriod reportPeriod) throws IOException, NoDataException {
        List<Currency> currencyResponseList =
                currencyRepository.findByDates(reportPeriod.startDate(),reportPeriod.endDate());
        if(currencyResponseList.isEmpty()){
            throw new NoDataException(); // RestControllerAdvise
        }
        else{
            return excelGenerator.generateExcelWorkbook(currencyResponseList);
        }
    }
    public byte[] generateExcelByCurrency(CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException {
        List<Currency> currencyResponseList =
                currencyRepository.findCurrencyByDates(
                        currencyReportPeriod.reportPeriod().startDate(),
                        currencyReportPeriod.reportPeriod().endDate(),
                        currencyReportPeriod.currencyCode());
        if(currencyResponseList.isEmpty()){
            throw new NoDataException();
        }
        else{
            return excelGenerator.generateExcelWorkbook(currencyResponseList);
        }

    }
    public byte[] generateExcelByCurrencyWithExtension(CurrencyReportPeriod currencyReportPeriod)
            throws IOException, NoDataException {
        List<Currency> currencyResponseList =
                currencyRepository.findCurrencyByDates(
                        currencyReportPeriod.reportPeriod().startDate(),
                        currencyReportPeriod.reportPeriod().endDate(),
                        currencyReportPeriod.currencyCode());
        if(currencyResponseList.isEmpty()){
            throw new NoDataException();
        }
        else{
            return excelGenerator.generateExcelWorkbookWithExtensions(currencyResponseList);
        }
    }
}
