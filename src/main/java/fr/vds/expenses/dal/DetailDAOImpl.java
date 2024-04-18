package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Detail;
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
public class DetailDAOImpl implements DetailDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_DETAIL_LINE_BY_LINE_group_id = "SELECT * FROM Details WHERE expense_id =:expense_id";
    private static final String CREATE_DETAIL = "insert into Details (expense_id, group_id, value, user_id) values (:expense_id, :group_id, :value, :user_id)";
    private static final String DELETE = "DELETE FROM Details WHERE id = :id";
    private static final String UPDATE = "UPDATE Details SET expense_id = :expense_id, group_id = :group_id, value = :value, user_id = :user_id WHERE id = :id";

    @Override
    public List<Detail> getLineDetailByLineId(int idExpenseLine) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", idExpenseLine);

        return namedParameterJdbcTemplate.query(SELECT_DETAIL_LINE_BY_LINE_group_id, mapSqlParameterSource, new DetailRowMapper());
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


    @Override
    public void deleteDetail(Detail detail){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", detail.getId());

        namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);

    }

    @Override
    public void updateDetail(Detail detail, int expenseId, int groupId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", detail.getId());
        mapSqlParameterSource.addValue("expense_id", expenseId);
        mapSqlParameterSource.addValue("group_id", groupId);
        mapSqlParameterSource.addValue("value", detail.getValue());
        mapSqlParameterSource.addValue("user_id", detail.getUser().getId());

        namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);

    }


}
    class DetailRowMapper implements RowMapper<Detail> {

        @Override
        public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
            Detail detail = new Detail();
            detail.setUser(new User(rs.getInt("user_id")));
            detail.setId(rs.getInt("id"));
            detail.setValue(rs.getFloat("value"));
            return detail;
        }
    }

