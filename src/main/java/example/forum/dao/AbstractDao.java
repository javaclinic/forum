package example.forum.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Generic DAO object that configures Spring JdbcTemplate for all DAOs.
 *  
 * @author nevenc
 *
 */
public abstract class AbstractDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected JdbcTemplate jdbcTemplate;

    public void setDatasource(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
        logger.debug("JdbcTemplate configured for {}", this.getClass().getSimpleName());
    }

}
