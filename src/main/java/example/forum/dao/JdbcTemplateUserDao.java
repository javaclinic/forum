package example.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import example.forum.domain.User;

/**
 * JdbcTemplateUserDao is UserDao implementation. It leverages Spring's JdbcTemplate utility methods.
 * 
 * @author nevenc
 *
 */
public class JdbcTemplateUserDao extends AbstractDao implements UserDao  {

    private static final String SQL_INSERT             = "INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE             = "UPDATE forum_users SET email=?, firstname=?, lastname=?, organization=?, title=?, password=? WHERE id=?";
    private static final String SQL_UPDATE_NOPASSWORD  = "UPDATE forum_users SET email=?, firstname=?, lastname=?, organization=?, title=? WHERE id=?";
    private static final String SQL_DELETE             = "DELETE FROM forum_users WHERE id=?";
    private static final String SQL_SELECT_ALL         = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users ORDER BY id";
    private static final String SQL_SELECT_BY_ID       = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users WHERE id=? ORDER BY id";
    private static final String SQL_SELECT_BY_EMAIL    = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users WHERE email=? ORDER BY id";

    /*
     * Slightly more complicated due to the fact we need to use KeyHolder to obtain just inserted entity.
     */
    @Override
    public User create(User user) throws DataAccessException {
        final User userToCreate = user;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int created = jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, userToCreate.getEmail());
                        ps.setString(2, userToCreate.getFirstname());
                        ps.setString(3, userToCreate.getLastname());
                        ps.setString(4, userToCreate.getOrganization());
                        ps.setString(5, userToCreate.getTitle());
                        ps.setString(6, userToCreate.getPassword());
                        return ps;
                    }
                },keyHolder);
        if ( created == 1) {
            user.setId(keyHolder.getKey().longValue());
            return user;
        } else {
            logger.error("Could not create a user.");
            throw new RuntimeException("Could not create a user.");
        }
    }

    @Override
    public User update(User user) throws DataAccessException {
        int updated;

        if ( user.getPassword() == null ) {
            updated = jdbcTemplate.update(SQL_UPDATE_NOPASSWORD,user.getEmail(),user.getFirstname(),user.getLastname(),user.getOrganization(),user.getTitle(),user.getId());
        } else {
            updated = jdbcTemplate.update(SQL_UPDATE,user.getEmail(),user.getFirstname(),user.getLastname(),user.getOrganization(),user.getTitle(),user.getPassword(),user.getId());
        }

        if ( updated == 1) { 
            return user;
        } else {
            logger.error("Could not update the user.");
            throw new RuntimeException("Could not update the user.");
        }
    }

    @Override
    public User delete(User user) throws DataAccessException {
        int deleted = jdbcTemplate.update(SQL_DELETE, user.getId());
        if ( deleted == 1) {
            return user;
        } else {
            logger.error("Could not create a user.");
            throw new RuntimeException("Could not create a user.");
        }
    }

    @Override
    public User findById(Long id) throws DataAccessException {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID,userRowMapper,id);
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL,userRowMapper,email);
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        return jdbcTemplate.query(SQL_SELECT_ALL,userRowMapper);
    }

    /*
     * Private helper function to map the rows from ResultSet
     */
    private RowMapper<User> userRowMapper = new RowMapper<User>() {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setOrganization(rs.getString("organization"));
            user.setTitle(rs.getString("title"));
            user.setPassword(rs.getString("password"));
            user.setCreated(rs.getTimestamp("created"));
            return user;
        }
    };

}
