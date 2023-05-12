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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Repository
@ConditionalOnProperty(
        value = "repository.mock.currency.mock.enable",
        havingValue = "false"
)
public class DatabaseCurrencyRepository implements CurrencyRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String UPDATE_CURRENCY_QUERY =
            "INSERT INTO Currency (currency,code,mid,date) VALUES (:currency,:code,:mid,:date)";

    private static final String FIND_ALL_CURRENCY_QUERY = "SELECT * from Currency";
    private static final String AVAILABLE_DATA_DISTINCT = "SELECT DISTINCT date FROM currency";
    private static final String FIND_BY_DATE = "SELECT * from Currency WHERE date = :date";
    private static final String FIND_BY_DATES = "SELECT * from Currency WHERE data <= :data1 AND data >= :data2";

    public DatabaseCurrencyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveAll(List<CurrencyResponse> currenciesResponse) {
        final List<Currency> currencies = currenciesResponse.stream()
                .map(Currency::from)
                .toList();
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(currencies);
        jdbcTemplate.batchUpdate(UPDATE_CURRENCY_QUERY, batch);
    }

    @Override
    public List<Currency> findByDates(String date1, String date2) {
        List<Currency> list1 = jdbcTemplate.query(FIND_BY_DATES,new MapSqlParameterSource("date1", date1)
                ,(rs,rowNum) -> mapToCurrency(rs));
        return list1; // nie działa do poprawy bo nie dany 2 parametr
    }

    @Override
    public List<Currency> findByDate(String date) {
        return jdbcTemplate.query(FIND_BY_DATE, new MapSqlParameterSource("date", date),
                (rs,rowNum) -> mapToCurrency(rs));
    }

    @Override
    public List<String> availableDate() {
        return jdbcTemplate.query(AVAILABLE_DATA_DISTINCT, (rs, rowNum) -> rs.getString("date"));
    }

    @Override
    public List<Currency> findAll() {
        return jdbcTemplate.query(FIND_ALL_CURRENCY_QUERY, (rs, rowNum) -> mapToCurrency(rs));
    }

    private Currency mapToCurrency(ResultSet rs) throws SQLException {
        return new Currency(
                rs.getString("currency"),
                rs.getString("code"),
                rs.getDouble("mid"),
                rs.getString("date")
        );
    }
}
