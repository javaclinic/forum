package example.forum.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import example.forum.domain.User;

@Repository
public class UserDaoHibernateImpl extends GenericDaoHibernateImpl<User> implements UserDao  {

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
        setClazz(User.class);
    }

    @Override
    public User findByEmail(String email) {
        String hqlQuery = "from User u where u.email like ? order by u.id";
        return super.findOne(hqlQuery, email);
    }

    @Override
    public List<User> findAll() {
        String hqlQuery = "from User u order by u.id";
        return super.findAll(hqlQuery);
    }

}
