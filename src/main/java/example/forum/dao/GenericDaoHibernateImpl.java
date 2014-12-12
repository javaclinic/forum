package example.forum.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic DAO class that implements most of the common CRUD methods.
 * 
 * @author nevenc
 *
 */
@Transactional(readOnly = true)
public abstract class GenericDaoHibernateImpl<T extends Serializable> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    protected final void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public T create(T entity) {
        this.getCurrentSession().save(entity);
        return entity;
    }

    @Transactional(readOnly = false)
    public T update(T entity) {
        this.getCurrentSession().update(entity);
        return entity;
    }

    @Transactional(readOnly = false)
    public T delete(T entity) {
        this.getCurrentSession().delete(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        return (T) this.getCurrentSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public T findOne(String hqlQuery, Object... parameters) {
        Query query = this.getCurrentSession().createQuery(hqlQuery);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i, parameters[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(String hqlQuery, Object... parameters) {
        Query query = this.getCurrentSession().createQuery(hqlQuery);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                query.setParameter(i, parameters[i]);
            }
        }
        return query.list();
    }

}
