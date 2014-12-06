package example.forum.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic API for CRUD operations on entity of serializable type T.
 * 
 * @author nevenc
 *
 * @param <T> Entity class
 */
public interface GenericDao<T extends Serializable> {
    T create(T entity);
    T update(T entity);
    T delete(T entity);
    T findById(Serializable id);
    List<T> findAll();
}
