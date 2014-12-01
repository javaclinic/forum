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
import example.forum.domain.Category;

public class JdbcCategoryDao extends AbstractDao implements CategoryDao  {

    private static final String SQL_INSERT             = "INSERT INTO forum_categories (name,description) VALUES (?,?)";
    private static final String SQL_UPDATE             = "UPDATE forum_categories SET name=?, description=? WHERE id=?";
    private static final String SQL_DELETE             = "DELETE FROM forum_categories WHERE id=?";
    private static final String SQL_SELECT_ALL         = "SELECT id, name, description FROM forum_categories ORDER BY id";
    private static final String SQL_SELECT_BY_ID       = "SELECT id, name, description FROM forum_categories WHERE id=? ORDER BY id";
    private static final String SQL_SELECT_BY_NAME     = "SELECT id, name, description FROM forum_categories WHERE email=? ORDER BY id";

    private DataSource datasource;

    public JdbcCategoryDao(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Category create(Category category) throws DataAccessException {
        if ( category == null ) throw new DataAccessException("Invalid category: null");
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            )
        {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());

            int created = ps.executeUpdate();
            if ( created == 1) {
                category.setId(this.getGeneratedKey(ps));
                return category;
            } else {
                logger.error("Could not create a category.");
                throw new DataAccessException("Could not create a category.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not create a category: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public Category update(Category category) throws DataAccessException {
        if ( category == null ) throw new DataAccessException("Invalid category: null");
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_UPDATE)
            )
        {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());

            int updated = ps.executeUpdate();
            if ( updated == 1) { 
                return category;
            } else {
                logger.error("Could not update the category.");
                throw new DataAccessException("Could not update the category.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not update the category: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public Category delete(Category category) throws DataAccessException {
        if ( category == null ) throw new DataAccessException("Invalid category: null");
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_DELETE);
            )
        {

            ps.setLong(1, category.getId());
            int deleted = ps.executeUpdate();
            if ( deleted == 1) {
                return category;
            } else {
                logger.error("Could not create a category.");
                throw new DataAccessException("Could not create a category.");
            }

        } catch (SQLException sqle) {
            logger.error("Could not create a category: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public Category findById(Long id) throws DataAccessException {
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_ID);
            )
        {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<Category> result = this.readRows(rs);
            if ( result.size() == 0 ) return null;
            if ( result.size() == 1 ) return result.get(0);
            throw new DataAccessException("Multiple entries founds: " + result.size());
        } catch (SQLException sqle) {
            logger.error("Could not find category by id: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public Category findByName(String name) throws DataAccessException {
        try
            (
                Connection c = datasource.getConnection();
                PreparedStatement ps = c.prepareStatement(SQL_SELECT_BY_NAME);
            )
        {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            List<Category> result = this.readRows(rs);
            if ( result.size() == 0 ) return null;
            if ( result.size() == 1 ) return result.get(0);
            throw new DataAccessException("Multiple entries founds: " + result.size());
        } catch (SQLException sqle) {
            logger.error("Could not find category by name: " + sqle.getMessage());
            throw new DataAccessException(sqle.getMessage(), sqle);
        }
    }

    @Override
    public List<Category> findAll() throws DataAccessException {
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
    private List<Category> readRows(ResultSet row) throws SQLException {
        List<Category> result = new ArrayList<>();
        while (row.next()) {
            Category category = new Category();
            category.setId(row.getLong("id"));
            category.setName(row.getString("name"));
            category.setDescription(row.getString("description"));
            result.add(category);
        }
        return result;
    }

}
