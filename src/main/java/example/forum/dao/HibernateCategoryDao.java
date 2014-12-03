package example.forum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import example.forum.domain.Category;

@Repository
public class HibernateCategoryDao extends AbstractDao implements CategoryDao  {

    @Override
    public Category create(Category category) {
        return (Category) super.create(category);
    }

    @Override
    public Category update(Category category) {
        return (Category) super.update(category);
    }

    @Override
    public Category delete(Category category) {
        return (Category) super.delete(category);
    }
 
    @Override
    public Category findById(Long id) {
        return (Category) super.findById(Category.class, id);
    }

    @Override
    public Category findByName(String name) {
        String hqlQuery = "from Category c where c.name=? order by c.id";
        return (Category) super.findOne(hqlQuery, name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        String hqlQuery = "from Category c order by c.id";
        return (List<Category>) super.findAll(hqlQuery);
    }

}
