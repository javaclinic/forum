package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.UserDao;

public class AbstractUserServlet extends SpringAbstractDaoServlet {

    private static final long serialVersionUID = 1L;
    protected UserDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = springContext.getBean(UserDao.class);
    }

}
