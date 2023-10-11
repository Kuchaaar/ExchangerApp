package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@ConditionalOnProperty(
        value = "repository.currency",
        havingValue = "jdbc"
)
public class DatabaseCurrencyRepository implements CurrencyRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String UPDATE_CURRENCY_QUERY =
            "INSERT INTO Currency (currency,code,mid,date) VALUES (:currency,:code,:mid,:date)";

    private static final String FIND_ALL_CURRENCY_QUERY = "SELECT * from Currency";
    private static final String AVAILABLE_DATA_DISTINCT = "SELECT DISTINCT date FROM currency";
    private static final String FIND_BY_DATE = "SELECT * from Currency WHERE date = :date";
    private static final String FIND_BY_DATES = "SELECT * from Currency WHERE date BETWEEN :date1 AND :date2";
    private static final String FIND_CURRENCY_BY_DATES
            = "SELECT * FROM Currency WHERE code = :code AND date BETWEEN :date1 AND :date2";

    public DatabaseCurrencyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveAll(List<CurrencyResponse> currenciesResponse){
        final List<Currency> currencies = currenciesResponse.stream()
                .map(Currency::from)
                .toList();
        final SqlParameterSource[] batch =
                SqlParameterSourceUtils.createBatch(currencies);
        jdbcTemplate.batchUpdate(UPDATE_CURRENCY_QUERY, batch);
    }

    @Override
    public List<Currency> findCurrencyByDates(LocalDate date1, LocalDate date2, String code){
        return jdbcTemplate.query(
                FIND_CURRENCY_BY_DATES,
                new MapSqlParameterSource()
                        .addValue("code", code)
                        .addValue("date1", date1)
                        .addValue("date2", date2),
                (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public List<Currency> findByDates(LocalDate date1, LocalDate date2){
        return jdbcTemplate.query(
                FIND_BY_DATES,
                new MapSqlParameterSource()
                        .addValue("date1", date1)
                        .addValue("date2", date2),
                (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public List<Currency> findByDate(LocalDate date){
        return jdbcTemplate.query(FIND_BY_DATE, new MapSqlParameterSource("date", date),
                (rs, rowNum) -> mapToCurrency(rs));
    }

    @Override
    public List<String> availableDates(){
        return jdbcTemplate.query(AVAILABLE_DATA_DISTINCT, (rs, rowNum) -> rs.getString("date"));
    }

    @Override
    public List<Currency> findAll(){
        return jdbcTemplate.query(FIND_ALL_CURRENCY_QUERY, (rs, rowNum) -> mapToCurrency(rs));
    }

    private Currency mapToCurrency(ResultSet rs) throws SQLException{
        return new Currency(
                rs.getString("currency"),
                rs.getString("code"),
                rs.getDouble("mid"),
                rs.getDate("date").toLocalDate()
        );
    }
}