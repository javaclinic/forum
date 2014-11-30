package example.forum.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.forum.dao.configuration.ForumPersistenceConfiguration;
import example.forum.dao.exceptions.DataAccessException;

public abstract class AbstractDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected EntityManager em = ForumPersistenceConfiguration.createEntityManager();

    protected Object create(Object entity) throws DataAccessException {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                logger.error("Could not rollback transaction: " + em, ex);
            }
            throw new DataAccessException("Could not create entity: " + entity, e);
        }
    }

    protected Object update(Object entity) throws DataAccessException {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return entity;
        } catch (RuntimeException e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                logger.error("Could not rollback transaction: " + em, ex);
            }
            throw new DataAccessException("Could not update entity: " + entity, e);
        }
    }

    protected Object delete(Object entity) throws DataAccessException {
        try {
            // use reflection to call getId method
            Method method = entity.getClass().getMethod("getId");
            Serializable id = (Serializable) method.invoke(entity);
            em.getTransaction().begin();
            Object found = this.findById(entity.getClass(), id);
            em.remove(found);
            em.getTransaction().commit();
            return found;
        } catch (RuntimeException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                logger.error("Could not rollback transaction: " + em, ex);
            }
            throw new DataAccessException("Could not delete entity: " + entity, e);
        }

    }

    protected Object findById(Class<?> clazz, Serializable id) throws DataAccessException {
        try {
            return em.find(clazz, id);
        } catch (RuntimeException e) {
            throw new DataAccessException("Could not find " + clazz.getSimpleName() + " by id: " + id, e);
        }
    }

    protected Object findOne(String jpqlQuery, Object... parameters) throws DataAccessException {
        try {
            Query query = em.createQuery(jpqlQuery);
            if ( parameters != null ) {
                for (int i=0; i<parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
            }
            return query.setMaxResults(1).getResultList();
        } catch (RuntimeException e) {
            throw new DataAccessException("Could not find an entity: " + jpqlQuery, e);
        }
    }

    protected List<?> findAll(String jpqlQuery, Object... parameters) throws DataAccessException {
        try {
            Query query = em.createQuery(jpqlQuery);
            if ( parameters != null ) {
                for (int i=0; i<parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new DataAccessException("Could not find an entity: " + jpqlQuery, e);
        }
    }

}
