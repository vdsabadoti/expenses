package fr.vds.expenses.dal;

import fr.vds.expenses.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LineDAOImpl implements LineDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_ALL_LINES_BY_EXPENSE_ID =
            "SELECT * FROM LinesOfExpense WHERE expense_id =:expense_id";

    private static final String FIND_BY_LINE_ID =
            "SELECT * FROM LinesOfExpense WHERE line_of_expense_id =:line_of_expense_id";


    @Override
    public List<Line> getAllLinesFromExpense(int idExpense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", idExpense);

        return namedParameterJdbcTemplate.query(SELECT_ALL_LINES_BY_EXPENSE_ID, mapSqlParameterSource, new LineRowMapper());
    }

    @Override
    public Line getLineFromExpense(int idLine){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("line_of_expense_id", idLine);

        return namedParameterJdbcTemplate.queryForObject(FIND_BY_LINE_ID, mapSqlParameterSource, new LineRowMapper());
    }

}

class LineRowMapper implements RowMapper<Line> {

    @Override
    public Line mapRow(ResultSet rs, int rowNum) throws SQLException {
        Line line = new Line();
        line.setIdLine(rs.getInt("line_of_expense_id"));
        line.setPayor(new User(rs.getInt("payor_id")));
        line.setLineDetailList(new ArrayList<LineDetail>());
        line.setValue(rs.getInt("value"));
        line.setDate(LocalDate.parse(rs.getString("date")));
        line.setLabel(rs.getString("label"));
        line.setDebtOrRefund(rs.getInt("debt_or_refund"));
        return line;
    }
}
