package example.forum.dao;

import java.util.List;

import example.forum.dao.exceptions.DataAccessException;
import example.forum.domain.User;

public class HibernateUserDao extends AbstractDao implements UserDao  {

    @Override
    public User create(User user) throws DataAccessException {
        return (User) super.create(user);
    }

    @Override
    public User update(User user) throws DataAccessException {
        return (User) super.update(user);
    }

    @Override
    public User delete(User user) throws DataAccessException {
        return (User) super.delete(user);
    }
 
    @Override
    public User findById(Long id) throws DataAccessException {
        return (User) super.findById(User.class, id);
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        String hqlQuery = "from User u where u.email like ? order by u.id";
        return (User) super.findOne(hqlQuery, email);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() throws DataAccessException {
        String hqlQuery = "from User u order by u.id";
        return (List<User>) super.findAll(hqlQuery);
    }

}
