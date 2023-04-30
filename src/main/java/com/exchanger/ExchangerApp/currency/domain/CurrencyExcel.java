package com.exchanger.ExchangerApp.currency.domain;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Component
public class CurrencyExcel {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private final String SqlQuery = "SELECT * FROM Currency";
    public CurrencyExcel(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Currency");
    public void writeToExcel() {
        jdbcTemplate.query(SqlQuery, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(columnValue);
                }
            }
        });

        try (FileOutputStream out = new FileOutputStream(new File("patchname\\excel.xlsx"))) {
            workbook.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
