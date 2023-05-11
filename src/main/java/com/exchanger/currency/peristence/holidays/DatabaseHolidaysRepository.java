package com.exchanger.currency.peristence.holidays;

import com.exchanger.currency.domain.holidays.HolidaysRepository;
import com.exchanger.currency.integration.holidays.HolidaysResponse;
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
        value = "repository.mock.holidays.mock.enable",
        havingValue = "false"
)
public class DatabaseHolidaysRepository implements HolidaysRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String INSERT_HOLIDAY_QUERY = "INSERT INTO Holidays (date, name) VALUES (:date, :name)";

    private static final String FIND_ALL_CURRENCY_QUERY = "SELECT date, name from Holidays";

    public DatabaseHolidaysRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void saveHolidays(List<HolidaysResponse> holidaysResponses) {
        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(holidaysResponses);
        jdbcTemplate.batchUpdate(INSERT_HOLIDAY_QUERY, batch);
    }

    @Override
    public List<HolidaysResponse> findHolidaysByYear() {
        return jdbcTemplate.query(FIND_ALL_CURRENCY_QUERY, (rs, rowNum) -> mapToHolidays(rs));
    }
    private HolidaysResponse mapToHolidays(ResultSet rs) throws SQLException {
        return new HolidaysResponse(
                rs.getString("date"),
                rs.getString("name")
        );
    }
}
