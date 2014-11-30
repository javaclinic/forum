package example.forum.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractDaoServlet is inherited by all Entity-CRUD-Servlets, and contains common
 * things like logger instantiation, injected datasource, various utility methods, etc.
 * 
 * @author nevenc
 *
 */
public class AbstractDaoServlet extends HttpServlet {

    @Resource(name="jdbc/forum")
    protected DataSource datasource;

    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean isEmpty(String value) {
        return value == null || value.length() == 0 || value.trim().length() == 0 ;
    }

}
