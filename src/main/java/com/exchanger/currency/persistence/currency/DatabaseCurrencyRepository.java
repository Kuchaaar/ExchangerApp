package com.exchanger.currency.persistence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import com.exchanger.currency.services.currencychange.CurrencyFromStartDateAndEndDate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyMap;

@Repository
@ConditionalOnProperty(value = "repository.currency", havingValue = "jdbc")
public class DatabaseCurrencyRepository implements CurrencyRepository{
    private static final String DELETE_ALL = "DELETE FROM currency";

    private static final String UPDATE_CURRENCY_QUERY =
            "INSERT INTO currency (currency,code,mid,date) VALUES (:currency,:code,:mid,:date)";
    private static final String FIND_ALL_CURRENCY = "SELECT * from currency";
    private static final String AVAILABLE_DATA_DISTINCT_COUNT = "SELECT COUNT(DISTINCT(date)) AS count FROM currency";
    private static final String AVAILABLE_DATE_DISTINCT =
            "SELECT DISTINCT(date) AS date FROM currency LIMIT :limit OFFSET :offset";
    private static final String IS_DATE_EXIST = "SELECT COUNT(*) > 0 FROM currency WHERE date=:date";
    private static final String FIND_BY_DATES =
            "SELECT * from currency WHERE date BETWEEN :date1 AND :date2";
    private static final String FIND_CURRENCY_BY_DATES =
            "SELECT * FROM currency WHERE code = :code AND date BETWEEN :date1 AND :date2";
    private static final String AVAILABLE_CODE_DISTINCT =
            "SELECT DISTINCT code FROM currency LIMIT :limit OFFSET :offset";
    private static final String AVAILABLE_CODE_DISTINCT_COUNT = "SELECT COUNT(DISTINCT(code)) AS count FROM currency";
    private static final String AVAILABLE_DATE_DISTINCT_BY_CODE =
            "SELECT DISTINCT date FROM currency WHERE code=:code LIMIT :limit OFFSET :offset";
    private static final String AVAILABLE_DATE_DISTINCT_BY_CODE_COUNT =
            "SELECT COUNT(DISTINCT(date)) AS count FROM currency WHERE code=:code";
    private static final String CURRENCY_FROM_START_AND_END_DATE =
            "SELECT c1.*, c2.* FROM currency c1, currency c2 WHERE c1.currency_id <> c2.currency_id AND c1.date = :startDate AND c2.date = :endDate AND c1.code = c2.code";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DatabaseCurrencyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveAll(List<CurrencyResponse> currenciesResponse){
        final List<Currency> currencies = currenciesResponse.stream().map(Currency::from).toList();
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(currencies);
        jdbcTemplate.batchUpdate(UPDATE_CURRENCY_QUERY, batch);
    }

    @Override
    public Page<String> availableCodes(Pageable pageable){
        List<String> results = jdbcTemplate.query(AVAILABLE_CODE_DISTINCT,
                parameterSource(pageable),
                (rs, rowNum) -> rs.getObject("code", String.class));
        return new PageImpl<>(results, pageable, countQuery(AVAILABLE_CODE_DISTINCT_COUNT));
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return jdbcTemplate.query(FIND_CURRENCY_BY_DATES,
                new MapSqlParameterSource().addValue("code", code)
                        .addValue("date1", date1)
                        .addValue("date2", date2),
                (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public List<CurrencyFromStartDateAndEndDate> findCurrencyFromStartDateAndEndDate(LocalDate startDate,
                                                                                     LocalDate endDate){

        return jdbcTemplate.query(CURRENCY_FROM_START_AND_END_DATE,
                new MapSqlParameterSource().addValue("startDate", startDate).addValue("endDate", endDate),
                (rs, rowNum) -> new CurrencyFromStartDateAndEndDate(new Currency(rs.getLong("currency_id"),
                        rs.getString("currency"),
                        rs.getString("code"),
                        rs.getBigDecimal("mid"),
                        rs.getDate("date").toLocalDate()),

                        new Currency(rs.getLong("currency_id"),
                                rs.getString("currency"),
                                rs.getString("code"),
                                rs.getBigDecimal("mid"),
                                rs.getDate("date").toLocalDate())));
    }


    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return jdbcTemplate.query(FIND_BY_DATES,
                new MapSqlParameterSource().addValue("date1", date1)
                        .addValue("date2", date2),
                (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public boolean isDateInData(LocalDate date){
        return jdbcTemplate.queryForObject(IS_DATE_EXIST, new MapSqlParameterSource("date", date), Boolean.class);
    }

    @Override
    public Page<LocalDate> availableDates(Pageable pageable){
        List<LocalDate> rows2 = jdbcTemplate.query(AVAILABLE_DATE_DISTINCT,
                parameterSource(pageable),
                (rs, rowNum) -> rs.getObject("date", LocalDate.class));
        return new PageImpl<>(rows2, pageable, countQuery(AVAILABLE_DATA_DISTINCT_COUNT));
    }

    @Override
    public List<Currency> findAll(){
        return jdbcTemplate.query(FIND_ALL_CURRENCY, (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public Page<LocalDate> availableDatesForCurrency(String code, Pageable pageable){
        List<LocalDate> result = jdbcTemplate.query(AVAILABLE_DATE_DISTINCT_BY_CODE,
                new MapSqlParameterSource().addValue("code", code)
                        .addValue("limit", pageable.getOffset() + pageable.getPageSize())
                        .addValue("offset", pageable.getOffset()),
                (rs, rowNum) -> rs.getObject("date", LocalDate.class));
        Long total = jdbcTemplate.query(AVAILABLE_DATE_DISTINCT_BY_CODE_COUNT,
                new MapSqlParameterSource().addValue("code", code),
                rs -> {
                    rs.next();
                    return rs.getObject("count", Long.class);
                });
        return new PageImpl<>(result, pageable, total);
    }

    public void deleteAll(){
        jdbcTemplate.update(DELETE_ALL, emptyMap());
    }

    private Long countQuery(String query){
        return jdbcTemplate.query(query, rs -> {
            rs.next();
            return rs.getObject("count", Long.class);
        });
    }

    private MapSqlParameterSource parameterSource(Pageable pageable){
        MapSqlParameterSource mysql = new MapSqlParameterSource();
        mysql.addValue("offset", pageable.getOffset());
        mysql.addValue("limit", pageable.getOffset() + pageable.getPageSize());
        return mysql;
    }

    private Currency mapToCurrency(ResultSet rs) throws SQLException{
        return new Currency(rs.getString("currency"),
                rs.getString("code"),
                rs.getBigDecimal("mid"),
                rs.getDate("date").toLocalDate());
    }
}