package example.forum.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import example.forum.domain.Category;

public class CategoryDaoHibernateImpl extends GenericDaoHibernateImpl<Category> implements CategoryDao  {

    public CategoryDaoHibernateImpl(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
        setClazz(Category.class);
    }

    @Override
    public Category findByName(String name) {
        String hqlQuery = "from Category c where c.name=? order by c.id";
        return super.findOne(hqlQuery, name);
    }

    @Override
    public List<Category> findAll() {
        String hqlQuery = "from Category c order by c.id";
        return super.findAll(hqlQuery);
    }

}
