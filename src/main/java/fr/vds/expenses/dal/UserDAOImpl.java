package fr.vds.expenses.dal;

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
public class UserDAOImpl implements UserDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TEST="INSERT INTO User (id_user, name, email) VALUES (:number, 'mulan', 'mulan@gmail.com');";
    private static final String MULAN_ROW_ID="SELECT * FROM User WHERE nickname = :nickname;";

    private static final String READ_ALL ="SELECT * FROM User";

    private static final String READ_BY_ID ="SELECT * FROM User WHERE user_id = :user_id;";

    @Override
    public void createMulan(){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("number", 1);

        namedParameterJdbcTemplate.update(TEST, mapSqlParameterSource);

    }

    @Override
    public User readMulanRowId(){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nickname", "mulan");

        return namedParameterJdbcTemplate.queryForObject(MULAN_ROW_ID, mapSqlParameterSource, new UserRowMapper());

    }

    @Override
    public User readUserById(int id){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", id);

        return namedParameterJdbcTemplate.queryForObject(READ_BY_ID, mapSqlParameterSource, new UserRowMapper());

    }

    @Override
    public List<User> readAllUsers(){
        return namedParameterJdbcTemplate.query(READ_ALL, new UserRowMapper());
    }




}

class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
       User user = new User();
       user.setIdUser(rs.getInt("user_id"));
       user.setUsername(rs.getString("nickname"));
       user.setMail(rs.getString("email"));
        return user;
    }
}
