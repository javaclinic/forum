package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.CategoryDao;
import example.forum.dao.JpaCategoryDao;

public class AbstractCategoryServlet extends AbstractDaoServlet {

    private static final long serialVersionUID = 1L;

    protected CategoryDao dao;

    @Override
    public void init() throws ServletException {
        logger.debug("Initializing " + this.getClass().getSimpleName() + ".");
        dao = new JpaCategoryDao();
    }

}
