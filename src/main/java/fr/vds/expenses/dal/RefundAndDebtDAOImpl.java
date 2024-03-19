package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Detail;
import fr.vds.expenses.bo.Group;
import fr.vds.expenses.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RefundAndDebtDAOImpl implements RefundAndDebtDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_DETAIL_LINE_BY_LINE_group_id =
            "SELECT * FROM Details WHERE expense_id =:expense_id";

    private static final String CREATE_DETAIL = "insert into Details (expense_id, group_id, value, user_id) values (:expense_id, :group_id, :value, :user_id)";


    @Override
    public List<Detail> getLineDetailByLineId(int idExpenseLine) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", idExpenseLine);

        return namedParameterJdbcTemplate.query(SELECT_DETAIL_LINE_BY_LINE_group_id, mapSqlParameterSource, new LineDetailRowMapper());
    }

    @Override
    public void createDetail(Detail detail, int groupId, int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", expenseId);
        mapSqlParameterSource.addValue("group_id", groupId);
        mapSqlParameterSource.addValue("value", detail.getValue());
        mapSqlParameterSource.addValue("user_id", detail.getUser().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_DETAIL, mapSqlParameterSource, keyHolder);

        detail.setId((Integer) keyHolder.getKey());
    }




}
    class LineDetailRowMapper implements RowMapper<Detail> {

        @Override
        public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
            Detail detail = new Detail();
            detail.setUser(new User(rs.getInt("user_id")));
            detail.setId(rs.getInt("id"));
            detail.setValue(rs.getFloat("value"));
            return detail;
        }
    }

