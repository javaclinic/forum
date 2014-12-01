package example.forum.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Spring (no-xml) configuration class. We use component scanning of packages for Spring components.
 * 
 * @author neven
 *
 */
@Configuration
@ComponentScan(basePackages="example.forum.dao")
public class ForumApplicationConfiguration {

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("org.hsqldb.jdbcDriver");
        datasource.setUrl("jdbc:hsqldb:hsql://localhost:9002/mydb");
        datasource.setUsername("sa");
        datasource.setPassword("");
        return datasource;
    }

}
