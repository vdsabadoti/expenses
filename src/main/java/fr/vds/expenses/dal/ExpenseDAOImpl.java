package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseDAOImpl implements ExpenseDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String READ_ALL_BY_USER="SELECT * FROM Expense WHERE owner_id = :owner_id";

    private final static String READ_ALL="SELECT * FROM Expense WHERE expense_id = :expense_id";

    @Override
    public List<Expense> getExpensesByUser(int userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("owner_id", userId);

        return namedParameterJdbcTemplate.query(READ_ALL_BY_USER, mapSqlParameterSource, new ExpenseRowMapper());

    }

    @Override
    public Expense getExpensesById(int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", expenseId);

        return namedParameterJdbcTemplate.queryForObject(READ_ALL, mapSqlParameterSource, new ExpenseRowMapper());

    }

}

class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        Expense expense = new Expense();
        expense.setIdExpense(rs.getInt("expense_id"));
        expense.setExpenseName(rs.getString("name"));
        expense.setBalance(rs.getInt("balance"));
        expense.setDescription(rs.getString("description"));
        expense.setBudgetByMonth(rs.getInt("budget_by_month"));
        expense.setParticipantList(new ArrayList<Participant>());
        return expense;
    }
}
