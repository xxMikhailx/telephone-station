package by.bsac.telephonestation.dao.impl;

import by.bsac.telephonestation.dao.LandlineDAO;
import by.bsac.telephonestation.domain.Landline;
import by.bsac.telephonestation.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository("landlineDAO")
public class LandlineDAOImpl implements LandlineDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String COLUMN_ID = "id_pk";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_SETUP_YEAR = "setup_year";
    private static final String COLUMN_LANDLINE_USER_ID = "user_id_pk";

    private static final String COLUMN_USER_LOGIN = "login";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_SURNAME = "surname";
    private static final String COLUMN_USER_ROLE = "role";
    private static final String COLUMN_USER_ADDRESS = "address";

    private static final String COLUMN_COUNT = "COUNT(*)";
    private static final String YEAR_PATTERN = "uuuu-MM-d";

    private static final String SQL_SELECT_LANDLINE_COUNT_AFTER_YEAR = "SELECT COUNT(*) FROM landlines WHERE setup_year >= ? GROUP BY id_pk";
    private static final String SQL_SELECT_LANDLINE_PHONE_NUMBER_BY_USER_SURNAME = "SELECT phone_number FROM landlines JOIN users ON (landlines.users_id_fk = users.id_pk) WHERE users.surname = ?";
    private static final String SQL_SELECT_ALL_LANDLINES ="SELECT landlines.id_pk,phone_number,setup_year,users.id_pk AS user_id_pk,login,password,role,name,surname,address FROM landlines JOIN users ON (landlines.users_id_fk = users.id_pk) ORDER BY landlines.id_pk";
    private static final String SQL_SELECT_LANDLINE_BY_ID = "SELECT landlines.id_pk,phone_number,setup_year,users.id_pk,login,password,role,name,surname,address FROM landlines JOIN users ON (landlines.users_id_fk = users.id_pk) WHERE landlines.id_pk = ?";
    private static final String SQL_CREATE_LANDLINE = "INSERT INTO landlines (phone_number,setup_year,users_id_fk) VALUES (?,?,?)";
    private static final String SQL_UPDATE_LANDLINE_BY_ID = "UPDATE landlines SET phone_number = ?,setup_year = ?,users_id_fk = ? WHERE id_pk = ?";
    private static final String SQL_DELETE_LANDLINE = "DELETE FROM landlines WHERE id_pk = ?";

    @Override
    public int findLandlineCountAfterYear(Year year) {
        return jdbcTemplate.query(SQL_SELECT_LANDLINE_COUNT_AFTER_YEAR,
                new Object[]{(short)year.getValue()},
                (rs -> rs.next()?rs.getInt(COLUMN_COUNT):0));
    }

    @Override
    public int findLandlineNumberByUserSurname(String surname) {
        return jdbcTemplate.query(SQL_SELECT_LANDLINE_PHONE_NUMBER_BY_USER_SURNAME,
                new Object[]{surname},
                (rs -> rs.next()?rs.getInt(COLUMN_PHONE_NUMBER):0));
    }

    @Override
    public List<Landline> findAllLandlines(){
        return jdbcTemplate.query(SQL_SELECT_ALL_LANDLINES, new LandlineMapper());
    }

    @Override
    public Landline findEntityById(Long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LANDLINE_BY_ID,
                new Object[]{id},
                new LandlineMapper());
    }

    @Override
    public boolean remove(Long id) {
        return jdbcTemplate.update(SQL_DELETE_LANDLINE, id) > 0;
    }

    @Override
    public boolean create(Landline landline) {
        return jdbcTemplate.update(SQL_CREATE_LANDLINE,
                landline.getPhoneNumber(),
                (short)landline.getSetupYear().getValue(),
                landline.getUser().getId()) > 0;
    }

    @Override
    public boolean update(Landline landline) {
        return jdbcTemplate.update(SQL_UPDATE_LANDLINE_BY_ID,
                landline.getPhoneNumber(),
                (short)landline.getSetupYear().getValue(),
                landline.getUser().getId(),
                landline.getId()) > 0;
    }

    private static final class LandlineMapper implements RowMapper<Landline> {

        public Landline mapRow(ResultSet rs, int rowNum) throws SQLException {
            Landline landline = new Landline();
            landline.setId(rs.getLong(COLUMN_ID));
            landline.setPhoneNumber(rs.getInt(COLUMN_PHONE_NUMBER));
            landline.setSetupYear(Year.parse(rs.getString(COLUMN_SETUP_YEAR), DateTimeFormatter.ofPattern(YEAR_PATTERN)));
            User user = new User();
            user.setId(rs.getLong(COLUMN_LANDLINE_USER_ID));
            user.setLogin(rs.getString(COLUMN_USER_LOGIN));
            user.setPassword(rs.getString(COLUMN_USER_PASSWORD));
            user.setRole(rs.getInt(COLUMN_USER_ROLE));
            user.setName(rs.getString(COLUMN_USER_NAME));
            user.setSurname(rs.getString(COLUMN_USER_SURNAME));
            user.setAddress(rs.getString(COLUMN_USER_ADDRESS));
            landline.setUser(user);
            return landline;
        }
    }
}
