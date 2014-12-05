package example.forum.dao;

import example.forum.domain.User;


/**
 * User DAO interface defines CRUD operations on User entity.
 * 
 * @author nevenc
 *
 */
public interface UserDao extends GenericDao<User> {
    User findByEmail(String email);
}
