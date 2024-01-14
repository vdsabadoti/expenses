package fr.vds.expenses.dal;

import fr.vds.expenses.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TEST="INSERT INTO User (id_user, name, email) VALUES (:number, 'mulan', 'mulan@gmail.com');";
    private static final String MULAN_ROW_ID="SELECT * FROM User WHERE nickname = :nickname;";

    private static final String READ_BY_ROW_ID="SELECT * FROM User WHERE _ROWID_ = :rowid;";

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
        mapSqlParameterSource.addValue("rowid", id);

        return namedParameterJdbcTemplate.queryForObject(READ_BY_ROW_ID, mapSqlParameterSource, new UserRowMapper());

    }




}

class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
       User user = new User();
       user.setIdUser(rs.getRow());
       user.setUsername(rs.getString("nickname"));
       user.setMail(rs.getString("email"));
        return user;
    }
}
