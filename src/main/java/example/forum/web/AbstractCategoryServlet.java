package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.CategoryDao;
import example.forum.dao.JdbcCategoryDao;

public class AbstractCategoryServlet extends AbstractDaoServlet {

    private static final long serialVersionUID = 1L;

    protected CategoryDao dao;

    @Override
    public void init() throws ServletException {
        logger.debug("Initializing " + this.getClass().getSimpleName() + " servlet with datasource:" + datasource);
        dao = new JdbcCategoryDao(datasource);
    }

}
