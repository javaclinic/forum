package example.forum.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Spring (no-xml) configuration class.
 * We use component scanning of packages for Spring components.
 * 
 * @author nevenc
 *
 */
@Configuration
@ComponentScan(basePackages="example.forum.dao")
@PropertySources({
    @PropertySource(value="classpath:application.properties",ignoreResourceNotFound=true),
    @PropertySource("classpath:database.properties")
})
public class ForumApplicationConfiguration {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    /**
     * Instantiates and returns a DataSource bean.
     * 
     * @return
     */
    @Bean
    public DataSource getDataSource() {
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName(env.getProperty("database.driverClassName"));
        datasource.setUrl(env.getProperty("database.url"));
        datasource.setUsername(env.getProperty("database.username"));
        datasource.setPassword(env.getProperty("database.password"));
        logger.debug("Instantiated datasource: {}", datasource);
        return datasource;
    }

    /**
     * Instantiates and returns a SessionFactory bean.
     * 
     * @param datasource
     * @return
     * @throws IOException
     */
    @Bean
    @Autowired
    public SessionFactory getSessionFactory(DataSource datasource) throws IOException {


        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setPackagesToScan("example.forum.domain");
        factory.setHibernateProperties(this.getHibernateProperties());
        factory.setDataSource(datasource);
        factory.afterPropertiesSet();
        SessionFactory sessionFactory = factory.getObject();

        logger.debug("Instantiated SessionFactory: {}", sessionFactory);
        return sessionFactory;

    }

    private Properties getHibernateProperties() throws IOException {
        Properties hibernateProperties = new Properties();
        hibernateProperties.load(this.getClass().getResourceAsStream("/hibernate.properties"));
        return hibernateProperties;
    }

}
