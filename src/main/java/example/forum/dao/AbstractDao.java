package example.forum.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.forum.dao.exceptions.DataAccessException;

public abstract class AbstractDao {

    private static final Logger classLogger = LoggerFactory.getLogger(AbstractDao.class);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final SessionFactory sessionFactory;

    static {
        try {
            classLogger.debug("Initializing Hibernate SessionFactory.");

            // Hibernate 3.x
            // sessionFactory = new Configuration().configure().buildSessionFactory();

            // Hibernate 4.x
            Configuration configuration = new Configuration();
            configuration.configure();

            // Hibernate 4.0, 4.1, 4.2
            // ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

            // Hibernate 4.3
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            classLogger.debug("Done initializing Hibernate SessionFactory.");

        } catch (Throwable thrown) {
            throw new ExceptionInInitializerError(thrown);
        }

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
            throw new DataAccessException("Could not create entity: " + entity, e);
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
            throw new DataAccessException("Could not create entity: " + entity, e);
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
            throw new DataAccessException("Could not create entity: " + entity, e);
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
            throw new DataAccessException("Could not find " + clazz.getSimpleName() + " by id: " + id, e);
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
            throw new DataAccessException("Could not find an entity: " + hqlQuery, e);
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
            throw new DataAccessException("Could not find all entities: " + hqlQuery, e);
        }
    }

}
