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
import org.springframework.dao.DataAccessException;

public abstract class AbstractDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session openSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    protected Object create(Object entity) throws DataAccessException {
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

    protected Object update(Object entity) throws DataAccessException {
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

    protected Object delete(Object entity) throws DataAccessException {
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

    protected Object findById(Class<?> clazz, Serializable id) throws DataAccessException {
        try {
            Session session = this.openSession();
            try {
                session.beginTransaction();
                try {
                    Object entity = session.get(clazz, id);
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

    protected Object findOne(String hqlQuery, Object... parameters) throws DataAccessException {
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
                    return query.uniqueResult();
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

    protected List<?> findAll(String hqlQuery, Object... parameters) throws DataAccessException {
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
            throw new RuntimeException("Could not find all entities entity: " + hqlQuery + " due to: " + e);
        }
    }

}
