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
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String READ_ALL_BY_USER="SELECT * FROM Groups WHERE owner_id = :owner_id";

    private final static String FIND_BY_ID ="SELECT * FROM Groups WHERE id = :id";
    private final static String FIND_ALL="SELECT * FROM Groups";

    private final static String CREATE_EXPENSE="INSERT INTO Groups (name, owner_id, description) VALUES\n" +
            "    (:name, :owner_id, :description);";

    private final static String UPDATE_GROUP="UPDATE Groups SET name = :name, description = :description WHERE id = :id";

    private final static String UPDATE_BUDGET="UPDATE Groups SET budget_by_month = :budget_by_month WHERE id = :id";

    private final static String DELETE_GROUP ="DELETE FROM Groups WHERE id = :id";

    @Override
    public List<Group> getExpensesByUser(int userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("owner_id", userId);

        return namedParameterJdbcTemplate.query(READ_ALL_BY_USER, mapSqlParameterSource, new GroupRowMapper());

    }

    @Override
    public Group getExpensesById(int expenseId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", expenseId);

        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new GroupRowMapper());

    }

    @Override
    public List<Group> getGroups(){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.query(FIND_ALL, mapSqlParameterSource, new GroupRowMapper());

    }

    @Override
    public void createGroup(Group group){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", group.getName());
        mapSqlParameterSource.addValue("owner_id", group.getOwner().getId());
        mapSqlParameterSource.addValue("description", group.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_EXPENSE, mapSqlParameterSource, keyHolder);

        group.setId((Integer) keyHolder.getKey());
    }

    @Override
    public void updateGroup(Group group){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", group.getName());
        mapSqlParameterSource.addValue("description", group.getDescription());
        mapSqlParameterSource.addValue("id", group.getId());

        namedParameterJdbcTemplate.update(UPDATE_GROUP, mapSqlParameterSource);
    }

    @Override
    public void updateBudgetExpense(int idExpense, int budget){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("budget_by_month", budget);
        mapSqlParameterSource.addValue("id", idExpense);

        namedParameterJdbcTemplate.update(UPDATE_BUDGET, mapSqlParameterSource);
    }

    @Override
    public void deleteExpense(int idExpense){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", idExpense);

        namedParameterJdbcTemplate.update(DELETE_GROUP, mapSqlParameterSource);
    }

}

class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setId(rs.getInt("id"));
        group.setName(rs.getString("name"));
        group.setBalance(rs.getFloat("balance"));
        group.setDescription(rs.getString("description"));
        group.setBudgetByMonth(rs.getFloat("budget_by_month"));
        group.setOwner(new User(rs.getInt("owner_id")));
        group.setParticipantList(new ArrayList<Participant>());
        return group;
    }
}
