package fr.vds.expenses.dal;

import fr.vds.expenses.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseDAOImpl implements ExpenseDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_ALL_LINES_BY_group_id =
            "SELECT * FROM Expenses WHERE group_id =:group_id";

    private static final String FIND_BY_LINE_ID =
            "SELECT * FROM Expenses WHERE id =:id";

    private static final String CREATE_EXPENSE = "insert into Expenses (group_id, label, value, payor_id, debt_or_refund, date) values (:group_id, :label, :value, :payor_id, :debt_or_refund, :date)";
    private static final String DELETE = "DELETE FROM Expenses WHERE id = :id";

    private static final String UPDATE = "UPDATE Expenses SET value = :value, group_id = :group_id, label = :label, payor_id = :payor_id, debt_or_refund = :debt_or_refund, date = :date WHERE id = :id";

    @Override
    public List<Expense> getAllLinesFromExpense(int idExpense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("group_id", idExpense);

        return namedParameterJdbcTemplate.query(SELECT_ALL_LINES_BY_group_id, mapSqlParameterSource, new ExpenseRowMapper());
    }

    @Override
    public Expense getLineFromExpense(int idLine){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", idLine);

        return namedParameterJdbcTemplate.queryForObject(FIND_BY_LINE_ID, mapSqlParameterSource, new ExpenseRowMapper());
    }

    @Override
    public void createExpense(int groupId, Expense expense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("group_id", groupId);
        mapSqlParameterSource.addValue("label", expense.getLabel());
        mapSqlParameterSource.addValue("value", expense.getValue());
        mapSqlParameterSource.addValue("payor_id", expense.getPayor().getId());
        mapSqlParameterSource.addValue("debt_or_refund", expense.getDebtOrRefund());
        mapSqlParameterSource.addValue("date", expense.getDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_EXPENSE, mapSqlParameterSource, keyHolder);

        expense.setId((Integer) keyHolder.getKey());
    }

    @Override
    public void deleteExpense(int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", expenseId);

        namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);

    }

    @Override
    public void updateExpense(Expense expense, int groupId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("group_id", groupId);
        mapSqlParameterSource.addValue("id", expense.getId());
        mapSqlParameterSource.addValue("label", expense.getLabel());
        mapSqlParameterSource.addValue("value", expense.getValue());
        mapSqlParameterSource.addValue("payor_id", expense.getPayor().getId());
        mapSqlParameterSource.addValue("debt_or_refund", expense.getDebtOrRefund());
        mapSqlParameterSource.addValue("date", expense.getDate());

        namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);

    }

}

class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        Expense expense = new Expense();
        expense.setId(rs.getInt("id"));
        expense.setPayor(new User(rs.getInt("payor_id")));
        expense.setDetailList(new ArrayList<Detail>());
        expense.setValue(rs.getFloat("value"));
        expense.setDate(LocalDate.parse(rs.getString("date")));
        expense.setLabel(rs.getString("label"));
        expense.setDebtOrRefund(rs.getInt("debt_or_refund"));
        return expense;
    }
}
