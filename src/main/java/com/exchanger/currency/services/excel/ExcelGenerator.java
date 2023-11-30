package com.exchanger.currency.services.excel;

import com.exchanger.currency.domain.exceptions.NoDataException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelGenerator {
    private final WorkbookGenerator workbookGenerator;

    public ExcelGenerator(WorkbookGenerator workbookGenerator){
        this.workbookGenerator = workbookGenerator;
    }

    public byte[] excelFileToByteArray(CurrencyReportDatasource currencyReportDatasource, boolean isExtension)
            throws IOException, NoDataException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbookGenerator.writeExcelFile(currencyReportDatasource, isExtension).write(outputStream);
        return outputStream.toByteArray();
    }
}
