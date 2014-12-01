package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.CategoryDao;

public class AbstractCategoryServlet extends SpringAbstractDaoServlet {

    private static final long serialVersionUID = 1L;

    protected CategoryDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = springContext.getBean(CategoryDao.class);
    }

}
