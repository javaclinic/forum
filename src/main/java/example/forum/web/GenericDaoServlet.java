package example.forum.web;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * GenericDaoServlet is a generic servlet, inherited by all Entity-CRUD-Servlets.
 * Contains common things like logger instantiation, injected Spring context,
 * various utility methods, etc.
 * 
 * @author nevenc
 *
 */
public class GenericDaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected WebApplicationContext springContext;

    @Override
    public void init() throws javax.servlet.ServletException {
        logger.debug("Initializing servlet: " + this.getClass());
        springContext  = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        logger.debug("Spring context is configured: " + springContext);
    }

    protected boolean isEmpty(String value) {
        return value == null || value.length() == 0 || value.trim().length() == 0 ;
    }

}
