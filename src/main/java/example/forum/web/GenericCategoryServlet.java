package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.CategoryDao;

/**
 * GenericCategoryServlet is a generic DAO servlet for all Category-related DAO servlets.
 * 
 * @author nevenc
 *
 */
public class GenericCategoryServlet extends GenericDaoServlet {

    private static final long serialVersionUID = 1L;
    protected CategoryDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = springContext.getBean(CategoryDao.class);
    }

}
