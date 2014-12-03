package example.forum.dao;

import java.util.List;

import example.forum.domain.Category;

/**
 * Category DAO interface defines CRUD operations on Category entity.
 * 
 * @author nevenc
 *
 */
public interface CategoryDao {
    Category create(Category category);
    Category update(Category category);
    Category delete(Category category);
    Category findById(Long id);
    Category findByName(String name);
    List<Category> findAll();
}
