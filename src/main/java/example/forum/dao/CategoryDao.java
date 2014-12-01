package example.forum.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import example.forum.domain.Category;

public interface CategoryDao {
    Category create(Category category) throws DataAccessException;
    Category update(Category category) throws DataAccessException;
    Category delete(Category category) throws DataAccessException;
    Category findById(Long id) throws DataAccessException;
    Category findByName(String name) throws DataAccessException;
    List<Category> findAll() throws DataAccessException;
}
