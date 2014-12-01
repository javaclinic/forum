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
import org.springframework.stereotype.Controller;

import example.forum.domain.Category;

/**
 * JdbcTemplateCategoryDao is UserDao implementation. It leverages Spring's JdbcTemplate utility methods.
 * 
 * @author nevenc
 *
 */
@Controller
public class JdbcTemplateCategoryDao extends AbstractDao implements CategoryDao  {

    private static final String SQL_INSERT             = "INSERT INTO forum_categories (name,description) VALUES (?,?)";
    private static final String SQL_UPDATE             = "UPDATE forum_categories SET name=?, description=? WHERE id=?";
    private static final String SQL_DELETE             = "DELETE FROM forum_categories WHERE id=?";
    private static final String SQL_SELECT_ALL         = "SELECT id, name, description FROM forum_categories ORDER BY id";
    private static final String SQL_SELECT_BY_ID       = "SELECT id, name, description FROM forum_categories WHERE id=? ORDER BY id";
    private static final String SQL_SELECT_BY_NAME     = "SELECT id, name, description FROM forum_categories WHERE email=? ORDER BY id";

    /*
     * Slightly more complicated due to the fact we need to use KeyHolder to obtain just inserted entity.
     */
    @Override
    public Category create(Category category) throws DataAccessException {
        final Category categoryToCreate = category;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int created = jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, categoryToCreate.getName());
                        ps.setString(2, categoryToCreate.getDescription());
                        return ps;
                    }
                },keyHolder);

        if ( created == 1) {
            category.setId(keyHolder.getKey().longValue());
            return category;
        } else {
            logger.error("Could not create a category.");
            throw new RuntimeException("Could not create a category.");
        }
    }

    @Override
    public Category update(Category category) throws DataAccessException {
        int updated = jdbcTemplate.update(SQL_UPDATE,category.getName(),category.getDescription(),category.getId());
        if ( updated == 1) { 
            return category;
        } else {
            logger.error("Could not update the category.");
            throw new RuntimeException("Could not update the category.");
        }
    }

    @Override
    public Category delete(Category category) throws DataAccessException {
        int deleted = jdbcTemplate.update(SQL_DELETE, category.getId());
        if ( deleted == 1) {
            return category;
        } else {
            logger.error("Could not create a category.");
            throw new RuntimeException("Could not create a category.");
        }
    }

    @Override
    public Category findById(Long id) throws DataAccessException {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID,categoryRowMapper,id);
    }

    @Override
    public Category findByName(String name) throws DataAccessException {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME,categoryRowMapper,name);
    }

    @Override
    public List<Category> findAll() throws DataAccessException {
        return jdbcTemplate.query(SQL_SELECT_ALL,categoryRowMapper);
    }

    /*
     * Private helper function to map the rows from ResultSet
     */
    private RowMapper<Category> categoryRowMapper = new RowMapper<Category>() {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            return category;
        }
    };

}
