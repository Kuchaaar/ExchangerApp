package com.exchanger.ExchangerApp.currency.Holidays;

import com.exchanger.ExchangerApp.currency.domain.Currency;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
@Qualifier("DatabaseHolidaysRepository")
public class DatabaseHolidaysRepository implements HolidaysRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String UPDATE_CURRENCY_QUERY = "INSERT INTO Holidays (date,name) VALUES (:date,:name)";

    private static final String FIND_ALL_CURRENCY_QUERY = "SELECT date, name from Holidays";

    public DatabaseHolidaysRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveHolidays(List<HolidaysResponse> holidaysResponses) {
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(holidaysResponses);
        jdbcTemplate.batchUpdate(UPDATE_CURRENCY_QUERY, batch);
    }

    @Override
    public List<HolidaysResponse> findHolidays() {
        return jdbcTemplate.query(FIND_ALL_CURRENCY_QUERY, (rs, rowNum) -> mapToHolidays(rs));
    }
    private HolidaysResponse mapToHolidays(ResultSet rs) throws SQLException {
        return new HolidaysResponse(
                rs.getDate("date"),
                rs.getString("name")
        );
    }
}
