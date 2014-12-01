package example.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import example.forum.dao.exceptions.DataAccessException;
import example.forum.domain.User;

public class JdbcUserDao extends AbstractDao implements UserDao  {

    private static final String SQL_INSERT             = "INSERT INTO forum_users (email,firstname,lastname,organization,title,password) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE             = "UPDATE forum_users SET email=?, firstname=?, lastname=?, organization=?, title=?, password=? WHERE id=?";
    private static final String SQL_UPDATE_NOPASSWORD  = "UPDATE forum_users SET email=?, firstname=?, lastname=?, organization=?, title=? WHERE id=?";
    private static final String SQL_DELETE             = "DELETE FROM forum_users WHERE id=?";
    private static final String SQL_SELECT_ALL         = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users ORDER BY id";
    private static final String SQL_SELECT_BY_ID       = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users WHERE id=? ORDER BY id";
    private static final String SQL_SELECT_BY_EMAIL    = "SELECT id, firstname, lastname, organization, title, email, password, created FROM forum_users WHERE email=? ORDER BY id";

    private DataSource datasource;

    public JdbcUserDao(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public User create(User user) throws DataAccessException {
        if ( user == null ) throw new DataAccessException("Invalid user: null");
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            )
        {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getOrganization());
            ps.setString(5, user.getTitle());
            ps.setString(6, user.getPassword());

            int created = ps.executeUpdate();
            if ( created == 1) {
                user.setId(this.getGeneratedKey(ps));
                return user;
            } else {
                logger.error("Could not create a user.");
                throw new DataAccessException("Could not create a user.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not create a user: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public User update(User user) throws DataAccessException {
        if ( user == null ) throw new DataAccessException("Invalid user: null");
        String sql = SQL_UPDATE;
        if ( user.getPassword() == null ) sql = SQL_UPDATE_NOPASSWORD;

        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)
            )
        {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getOrganization());
            ps.setString(5, user.getTitle());
            if ( user.getPassword() != null ) {
                ps.setString(6, user.getPassword());
                ps.setLong(7, user.getId());
            } else {
                ps.setLong(6, user.getId());
            }

            int updated = ps.executeUpdate();
            if ( updated == 1) { 
                return user;
            } else {
                logger.error("Could not update the user.");
                throw new DataAccessException("Could not update the user.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not update the user: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public User delete(User user) throws DataAccessException {
        if ( user == null ) throw new DataAccessException("Invalid user: null");
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_DELETE);
            )
        {

            ps.setLong(1, user.getId());
            int deleted = ps.executeUpdate();
            if ( deleted == 1) {
                return user;
            } else {
                logger.error("Could not create a user.");
                throw new DataAccessException("Could not create a user.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not create a user: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public User findById(Long id) throws DataAccessException {
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_ID);
            )
        {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<User> result = this.readRows(rs);
            if ( result.size() == 0 ) return null;
            if ( result.size() == 1 ) return result.get(0);
            throw new DataAccessException("Multiple entries founds: " + result.size());

        } catch (SQLException sqle) {
            logger.error("Could not find user by id: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_EMAIL);
            )
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            List<User> result = this.readRows(rs);
            if ( result.size() == 0 ) return null;
            if ( result.size() == 1 ) return result.get(0);
            throw new DataAccessException("Multiple entries founds: " + result.size());
        } catch (SQLException sqle) {
            logger.error("Could not find user by email: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_SELECT_ALL);
            )
        {
            ResultSet rs = ps.executeQuery();
            return this.readRows(rs);
        } catch (SQLException sqle) {
            logger.error("Could not find all categories: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }
 

    /*
     * Private helper function to read rows from ResultSet.
     */
    private List<User> readRows(ResultSet row) throws SQLException {
        List<User> result = new ArrayList<>();
        while (row.next()) {
            User user = new User();
            user.setId(row.getLong("id"));
            user.setEmail(row.getString("email"));
            user.setFirstname(row.getString("firstname"));
            user.setLastname(row.getString("lastname"));
            user.setOrganization(row.getString("organization"));
            user.setTitle(row.getString("title"));
            user.setPassword(row.getString("password"));
            user.setCreated(row.getTimestamp("created"));
            result.add(user);
        }
        return result;
    }

}
