package example.forum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import example.forum.domain.User;

@Repository
public class UserDaoHibernateImpl extends GenericDaoHibernateImpl<User> implements UserDao  {

    public UserDaoHibernateImpl() {
        setClazz(User.class);
    }

    @Override
    @Transactional(readOnly=true)
    public User findByEmail(String email) {
        String hqlQuery = "from User u where u.email like ? order by u.id";
        return super.findOne(hqlQuery, email);
    }

    @Override
    @Transactional(readOnly=true)
    public List<User> findAll() {
        String hqlQuery = "from User u order by u.id";
        return super.findAll(hqlQuery);
    }

}
