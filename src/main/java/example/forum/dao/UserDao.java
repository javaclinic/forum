package example.forum.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import example.forum.domain.User;

public interface UserDao {
    User create(User user) throws DataAccessException;
    User update(User user) throws DataAccessException;
    User delete(User user) throws DataAccessException;
    User findById(Long id) throws DataAccessException;
    User findByEmail(String email) throws DataAccessException;
    List<User> findAll() throws DataAccessException;
}
