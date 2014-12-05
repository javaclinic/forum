package example.forum.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Generic DAO class that implements most of the common CRUD methods.
 * 
 * @author nevenc
 *
 */
public abstract class GenericDaoHibernateImpl<T extends Serializable> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> clazz;

    @Autowired
    private SessionFactory sessionFactory;

    protected final void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    private Session openSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public T create(T entity) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    session.save(entity);
                    session.getTransaction().commit();
                    return entity;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not create entity: " + entity + " due to: " + e);
        }
    }

    public T update(T entity) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    session.update(entity);
                    session.getTransaction().commit();
                    return entity;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not update entity: " + entity + " due to: " + e);
        }
    }

    public T delete(T entity) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    session.delete(entity);
                    session.getTransaction().commit();
                    return entity;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not delete entity: " + entity + " due to: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    T entity = (T) session.get(clazz, id);
                    session.getTransaction().commit();
                    return entity;
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not find entity by id=" + id + " due to: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    public T findOne(String hqlQuery, Object... parameters) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    Query query = session.createQuery(hqlQuery);
                    if ( parameters != null ) {
                        for (int i=0; i<parameters.length; i++) {
                            query.setParameter(i, parameters[i]);
                        }
                    }
                    session.getTransaction().commit();
                    return (T) query.uniqueResult();
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not find an entity: " + hqlQuery + " due to: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(String hqlQuery, Object... parameters) {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    Query query = session.createQuery(hqlQuery);
                    if ( parameters != null ) {
                        for (int i=0; i<parameters.length; i++) {
                            query.setParameter(i, parameters[i]);
                        }
                    }
                    session.getTransaction().commit();
                    return query.list();
                } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    throw e;
                }
            } finally {
                if ( session != null ) session.close();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find all entities entity: " + hqlQuery + " due to: " + e);
        }
    }

}
