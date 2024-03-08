package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
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
public class ParticipantDAOImpl implements ParticipantDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_PARTICIPANTS_BY_USER_ID = "SELECT * FROM ParticipantsOfExpense WHERE user_id = :user_id;";
    private static final String SELECT_PARTICIPANTS_BY_ID = "SELECT * FROM ParticipantsOfExpense WHERE participant_id = :participant_id;";

    private static final String SELECT_PARTICIPANTS_BY_EXPENSE="SELECT * FROM ParticipantsOfExpense WHERE expense_id = :expense_id;";

    private static final String CREATE_PARTICIPANT="INSERT INTO ParticipantsOfExpense (expense_id, user_id, budget_by_month) VALUES (:expense_id, :user_id, :budget_by_month);";

    private final static String DELETE_PARTICIPANT="DELETE FROM ParticipantsOfExpense WHERE participant_id = :participant_id";

    private static final String COUNT_PARTICIPANTS_FROM_EXPENSE_BY_USER_ID="SELECT COUNT(*) FROM ParticipantsOfExpense WHERE user_id = :user_id AND expense_id = :expense_id";
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

    @Override
    public Participant getParticipantById(int participantId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("participant_id", participantId);

        return namedParameterJdbcTemplate.queryForObject(SELECT_PARTICIPANTS_BY_ID, mapSqlParameterSource, new ParticipantRowMapper());

    }

    @Override
    public void createPaticipant(Participant participant){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("expense_id", participant.getExpense().getIdExpense());
        mapSqlParameterSource.addValue("user_id", participant.getUser().getIdUser());
        mapSqlParameterSource.addValue("budget_by_month", participant.getBudgetByMonth());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_PARTICIPANT, mapSqlParameterSource, keyHolder);

        participant.setIdParticipant((Integer) keyHolder.getKey());

    }

    @Override
    public void deleteParticipant(int idParticipant){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("participant_id", idParticipant);

        namedParameterJdbcTemplate.update(DELETE_PARTICIPANT, mapSqlParameterSource);
    }

    @Override
    public int countParticipantsByUserId(int idIUser, int idExpense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", idIUser);
        mapSqlParameterSource.addValue("expense_id", idExpense);

        return namedParameterJdbcTemplate.queryForObject(COUNT_PARTICIPANTS_FROM_EXPENSE_BY_USER_ID, mapSqlParameterSource, Integer.class);
    }

}

class ParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Participant();
        participant.setExpense(new Expense(rs.getInt("expense_id")));
        participant.setIdParticipant(rs.getInt("participant_id"));
        participant.setBudgetByMonth(rs.getFloat("budget_by_month"));
        participant.setUser(new User(rs.getInt("user_id")));
        return participant;
    }
}
