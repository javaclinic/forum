package example.forum.dao;

import example.forum.domain.Category;

/**
 * Category DAO interface defines CRUD operations on Category entity.
 * 
 * @author nevenc
 *
 */
public interface CategoryDao extends GenericDao<Category> {
    Category findByName(String name);
}
