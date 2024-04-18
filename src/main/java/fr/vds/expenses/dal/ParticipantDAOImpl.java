package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Group;
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

    private static final String SELECT_PARTICIPANTS_BY_USER_ID = "SELECT * FROM Participants WHERE user_id = :user_id;";
    private static final String SELECT_PARTICIPANTS_BY_ID = "SELECT * FROM Participants WHERE id = :id;";

    private static final String SELECT_PARTICIPANTS_BY_EXPENSE="SELECT * FROM Participants WHERE group_id = :group_id;";

    private static final String CREATE_PARTICIPANT="INSERT INTO Participants (group_id, user_id, budget_by_month) VALUES (:group_id, :user_id, :budget_by_month);";

    private final static String DELETE_PARTICIPANT="DELETE FROM Participants WHERE id = :id";

    private final static String UPDATE_PARTICIPANT="UPDATE Participants SET budget_by_month = :budget_by_month WHERE id = :id";

    private static final String COUNT_PARTICIPANTS_FROM_EXPENSE_BY_USER_ID="SELECT COUNT(*) FROM Participants WHERE user_id = :user_id AND group_id = :group_id";
    @Override
    public List<Participant> readParticipantsByUserId(int userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);

        return namedParameterJdbcTemplate.query(SELECT_PARTICIPANTS_BY_USER_ID, mapSqlParameterSource, new ParticipantRowMapper());

    }

    @Override
    public List<Participant> getAllTheParticipantsOfTheExpense(int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("group_id", expenseId);

        return namedParameterJdbcTemplate.query(SELECT_PARTICIPANTS_BY_EXPENSE, mapSqlParameterSource, new ParticipantRowMapper());

    }

    @Override
    public Participant getParticipantById(int participantId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", participantId);

        return namedParameterJdbcTemplate.queryForObject(SELECT_PARTICIPANTS_BY_ID, mapSqlParameterSource, new ParticipantRowMapper());

    }

    @Override
    public void createPaticipant(Participant participant){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("group_id", participant.getExpense().getId());
        mapSqlParameterSource.addValue("user_id", participant.getUser().getId());
        mapSqlParameterSource.addValue("budget_by_month", participant.getBudgetByMonth());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_PARTICIPANT, mapSqlParameterSource, keyHolder);

        participant.setId((Integer) keyHolder.getKey());

    }

    @Override
    public void updateParticipant(Participant participant){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("budget_by_month", participant.getBudgetByMonth());
        mapSqlParameterSource.addValue("id", participant.getId());

        namedParameterJdbcTemplate.update(UPDATE_PARTICIPANT, mapSqlParameterSource);

    }

    @Override
    public void deleteParticipant(int idParticipant){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", idParticipant);

        namedParameterJdbcTemplate.update(DELETE_PARTICIPANT, mapSqlParameterSource);
    }

    @Override
    public int countParticipantsByUserId(int idIUser, int idExpense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", idIUser);
        mapSqlParameterSource.addValue("group_id", idExpense);

        return namedParameterJdbcTemplate.queryForObject(COUNT_PARTICIPANTS_FROM_EXPENSE_BY_USER_ID, mapSqlParameterSource, Integer.class);
    }

}

class ParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Participant();
        participant.setExpense(new Group(rs.getInt("group_id")));
        participant.setId(rs.getInt("id"));
        participant.setBudgetByMonth(rs.getFloat("budget_by_month"));
        participant.setUser(new User(rs.getInt("user_id")));
        return participant;
    }
}
