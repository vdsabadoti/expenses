package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
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
public class ParticipantDAOImpl implements ParticipantDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_PARTICIPANTS_BY_USER_ID = "SELECT * FROM ParticipantsOfExpense WHERE user_id = :user_id;";

    private static final String SELECT_PARTICIPANTS_BY_EXPENSE="SELECT * FROM ParticipantsOfExpense WHERE expense_id = :expense_id;";
    @Override
    public List<Participant> readParticipantsByUserId(int userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);

        return namedParameterJdbcTemplate.query(SELECT_PARTICIPANTS_BY_USER_ID, mapSqlParameterSource, new ParticipantRowMapper());

    }

    @Override
    public List<Participant> getAllTheParticipantsOfTheExpense(int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", expenseId);

        return namedParameterJdbcTemplate.query(SELECT_PARTICIPANTS_BY_EXPENSE, mapSqlParameterSource, new ParticipantRowMapper());

    }

}

class ParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Participant();
        participant.setExpense(new Expense(rs.getInt("expense_id")));
        participant.setIdParticipant(rs.getInt("participant_id"));
        participant.setBudgetByMonth(rs.getInt("budget_by_month"));
        participant.setUser(new User(rs.getInt("user_id")));
        return participant;
    }
}
