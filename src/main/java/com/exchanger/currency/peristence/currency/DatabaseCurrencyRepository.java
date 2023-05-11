package com.exchanger.currency.peristence.currency;

import com.exchanger.currency.domain.currency.Currency;
import com.exchanger.currency.domain.currency.CurrencyRepository;
import com.exchanger.currency.integration.currency.CurrencyResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public List<Currency> findAll() {
        return jdbcTemplate.query(FIND_ALL_CURRENCY_QUERY, (rs, rowNum) -> mapToCurrency(rs));
    }

    public Currency mapToCurrency(ResultSet rs) throws SQLException {
        return new Currency(
                rs.getString("currency"),
                rs.getString("code"),
                rs.getDouble("mid"),
                rs.getString("date")
        );
    }
}