package example.forum.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.forum.dao.exceptions.DataAccessException;

public abstract class AbstractDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataSource datasource;

    protected void setDatasource(DataSource datasource) {
        this.datasource = datasource;
        logger.info("Datasource set: " + this.datasource);
    }

    protected void setDataSource(String jndiName) throws DataAccessException {
        Context naming = null;
        try {
            naming = new InitialContext();
            this.datasource = (DataSource) naming.lookup(jndiName);
            logger.info("Datasource set: " + this.datasource);
        } catch (NamingException ne) {
            logger.error(ne.getMessage());
            throw new DataAccessException("Could not create a datasource from jndi: " + ne.getMessage(),ne.getCause());
        }
    }

    protected Long getGeneratedKey(PreparedStatement ps)  throws DataAccessException, SQLException {
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if ( generatedKeys.next() ) {
            return generatedKeys.getLong(1);
        } else {
            throw new DataAccessException("Could not get database-generated key.");
        }
    }

}
