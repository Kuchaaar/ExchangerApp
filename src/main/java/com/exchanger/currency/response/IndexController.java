package com.exchanger.currency.response;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.excel.DateConverter;
import com.exchanger.currency.excel.ExcelMaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController("/")
public class IndexController {
    private final CurrencyRepository currencyRepository;
    private final ExcelMaker excelMaker;
    private final DateConverter dateConverter;

    public IndexController(CurrencyRepository currencyRepository, ExcelMaker excelMaker, DateConverter dateConverter) {
        this.currencyRepository = currencyRepository;
        this.excelMaker = excelMaker;
        this.dateConverter = dateConverter;
    }

    @PostMapping("dane")
    public ResponseEntity<byte[]> excelResponse(@RequestBody String dates) throws IOException {
    List<LocalDate> list = dateConverter.stringIntoLocalDates(dates);
    return excelMaker.generateExcel(list.get(0),list.get(1));
    }
    @GetMapping("daty")
    public List<String> getLocalDates(){
        return currencyRepository.availableDates();
    }

}
