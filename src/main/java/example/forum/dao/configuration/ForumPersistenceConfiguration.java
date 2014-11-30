package example.forum.dao.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ForumPersistenceConfiguration is a web listener that configures and instantiates EntityManagerFactory in the ServletContext.
 * 
 * @author nevenc
 *
 */
@WebListener
public class ForumPersistenceConfiguration implements ServletContextListener {

    protected final static Logger logger = LoggerFactory.getLogger(ForumPersistenceConfiguration.class);

    private static EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.debug("Initializing ForumPersistenceConfiguration...");
        entityManagerFactory = Persistence.createEntityManagerFactory("forum");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.debug("Destroying EntityManagerFactory...");
        entityManagerFactory.close();
    }

    public static EntityManager createEntityManager() {
        logger.debug("Creating EntityManager...");
        if (entityManagerFactory == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return entityManagerFactory.createEntityManager();
    }

}

