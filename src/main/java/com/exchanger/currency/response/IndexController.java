package com.exchanger.currency.response;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.excel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class IndexController {
    private final CurrencyRepository currencyRepository;
    private final ExcelMaker excelMaker;
    private final DateConverter dateConverter;
    private final CurrencyConverter currencyConverter;

    public IndexController(CurrencyRepository currencyRepository, ExcelMaker excelMaker, DateConverter dateConverter, CurrencyConverter currencyConverter) {
        this.currencyRepository = currencyRepository;
        this.excelMaker = excelMaker;
        this.dateConverter = dateConverter;
        this.currencyConverter = currencyConverter;
    }

    @PostMapping(value = "/dane", produces = "application/octet-stream")
    public ResponseEntity<byte[]> excelResponse(@RequestBody String dates) throws IOException {
        ReportPeriod list = dateConverter.stringIntoReportPeriod(dates);

        return ResponseEntity.ok()
                .headers(headers -> headers.add("attachment", "excel.xlsx"))
                .body(excelMaker.generateExcel(list));
    }
    @PostMapping(value="/currency",produces = "application/octet-stream")
    public ResponseEntity<byte[]> oneCurrencyExcelResponse(@RequestBody String data) throws IOException {
        CurrencyReportPeriod currencyReportPeriod = currencyConverter.currencyConvertion(data);
        return ResponseEntity.ok()
                .headers(headers -> headers.add("attachment", "excel.xlsx"))
                .body(excelMaker.generateExcelByCurrency(currencyReportPeriod));
    }
    @GetMapping("/daty")
    public List<String> getLocalDates(){
        return currencyRepository.availableDates();
    }

}
