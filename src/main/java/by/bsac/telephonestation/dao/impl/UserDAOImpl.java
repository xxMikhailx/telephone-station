package by.bsac.telephonestation.dao.impl;

import by.bsac.telephonestation.dao.UserDAO;
import by.bsac.telephonestation.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String COLUMN_ID = "id_pk";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_ADDRESS = "address";

    private static final String SQL_SELECT_ALL_USERS ="SELECT id_pk,login,password,role,name,surname,address FROM users ORDER BY id_pk";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_pk,login,password,role,name,surname,address FROM users WHERE id_pk = ?";
    private static final String SQL_CREATE_USER = "INSERT INTO users (login,password,role,name,surname,address) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE users SET login = ?,password = ?,role = ?,name = ?,surname = ?,address = ? WHERE id_pk = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id_pk = ?";

    @Override
    public List<User> findAllUsers(){
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, new UserMapper());
    }

    @Override
    public User findEntityById(Long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID,
                new Object[]{id},
                new UserMapper());
    }

    @Override
    public boolean remove(Long id) {
        return jdbcTemplate.update(SQL_DELETE_USER, id) > 0;
    }

    @Override
    public boolean create(User user) {
        return jdbcTemplate.update(SQL_CREATE_USER,
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getName(),
                user.getSurname(),
                user.getAddress()) > 0;
    }

    @Override
    public boolean update(User user) {
        return jdbcTemplate.update(SQL_UPDATE_USER_BY_ID,
                user.getLogin(),
                user.getPassword(),
                user.getRole(),
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getId()) > 0;
    }

    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong(COLUMN_ID));
            user.setLogin(rs.getString(COLUMN_LOGIN));
            user.setPassword(rs.getString(COLUMN_PASSWORD));
            user.setRole(rs.getInt(COLUMN_ROLE));
            user.setName(rs.getString(COLUMN_NAME));
            user.setSurname(rs.getString(COLUMN_SURNAME));
            user.setAddress(rs.getString(COLUMN_ADDRESS));
            return user;
        }
    }
}
