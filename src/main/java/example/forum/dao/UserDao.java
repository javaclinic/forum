package example.forum.dao;

import java.util.List;

import example.forum.domain.User;

/**
 * User DAO interface defines CRUD operations on User entity.
 * 
 * @author nevenc
 *
 */
public interface UserDao {
    User create(User user);
    User update(User user);
    User delete(User user);
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll();
}
