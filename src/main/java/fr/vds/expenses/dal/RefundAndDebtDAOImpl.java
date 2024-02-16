package fr.vds.expenses.dal;

import fr.vds.expenses.bo.LineDetail;
import fr.vds.expenses.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RefundAndDebtDAOImpl implements RefundAndDebtDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_DETAIL_LINE_BY_LINE_EXPENSE_ID =
            "SELECT * FROM DetailsOfALine WHERE line_of_expense_id =:line_of_expense_id";


    @Override
    public List<LineDetail> getLineDetailByLineId(int idExpenseLine) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("line_of_expense_id", idExpenseLine);

        return namedParameterJdbcTemplate.query(SELECT_DETAIL_LINE_BY_LINE_EXPENSE_ID, mapSqlParameterSource, new LineDetailRowMapper());
    }
}
    class LineDetailRowMapper implements RowMapper<LineDetail> {

        @Override
        public LineDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            LineDetail lineDetail = new LineDetail();
            lineDetail.setUser(new User(rs.getInt("user_id")));
            lineDetail.setIdLineDetail(rs.getInt("detail_of_a_line_id"));
            lineDetail.setValue(rs.getInt("value"));
            return lineDetail;
        }
    }

