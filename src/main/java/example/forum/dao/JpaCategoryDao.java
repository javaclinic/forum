package example.forum.dao;

import java.util.List;

import example.forum.dao.exceptions.DataAccessException;
import example.forum.domain.Category;

public class JpaCategoryDao extends AbstractDao implements CategoryDao  {

    @Override
    public Category create(Category category) throws DataAccessException {
        return (Category) super.create(category);
    }

    @Override
    public Category update(Category category) throws DataAccessException {
        return (Category) super.update(category);
    }

    @Override
    public Category delete(Category category) throws DataAccessException {
        return (Category) super.delete(category);
    }
 
    @Override
    public Category findById(Long id) throws DataAccessException {
        return (Category) super.findById(Category.class, id);
    }

    @Override
    public Category findByName(String name) throws DataAccessException {
        String hqlQuery = "SELECT c FROM Category c WHERE c.name=? ORDER BY c.id";
        return (Category) super.findOne(hqlQuery, name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findAll() throws DataAccessException {
        String hqlQuery = "SELECT c FROM Category c ORDER BY c.id";
        return (List<Category>) super.findAll(hqlQuery);
    }

}
