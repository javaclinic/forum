package example.forum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.forum.domain.Category;

@Repository
public class CategoryDaoHibernateImpl extends GenericDaoHibernateImpl<Category> implements CategoryDao  {

    public CategoryDaoHibernateImpl() {
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
