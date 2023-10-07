package com.exchanger.currency.response;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.excel.DateConverter;
import com.exchanger.currency.excel.ExcelMaker;
import com.exchanger.currency.excel.ReportPeriod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class IndexController {
    private final CurrencyRepository currencyRepository;
    private final ExcelMaker excelMaker;
    private final DateConverter dateConverter;

    public IndexController(CurrencyRepository currencyRepository, ExcelMaker excelMaker, DateConverter dateConverter) {
        this.currencyRepository = currencyRepository;
        this.excelMaker = excelMaker;
        this.dateConverter = dateConverter;
    }

    @PostMapping("/dane")
    public ResponseEntity<byte[]> excelResponse(@RequestBody String dates) throws IOException {
    ReportPeriod list = dateConverter.stringIntoReportPeriod(dates);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", "plik-excela.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelMaker.generateExcel(list));
    }
    @GetMapping("/daty")
    public List<String> getLocalDates(){
        return currencyRepository.availableDates();
    }

}
