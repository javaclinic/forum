package example.forum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.forum.domain.User;

@Repository
public class HibernateUserDao extends AbstractDao implements UserDao  {

    @Override
    public User create(User user) {
        return (User) super.create(user);
    }

    @Override
    public User update(User user) {
        return (User) super.update(user);
    }

    @Override
    public User delete(User user) {
        return (User) super.delete(user);
    }
 
    @Override
    public User findById(Long id) {
        return (User) super.findById(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        String hqlQuery = "from User u where u.email like ? order by u.id";
        return (User) super.findOne(hqlQuery, email);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        String hqlQuery = "from User u order by u.id";
        return (List<User>) super.findAll(hqlQuery);
    }

}
