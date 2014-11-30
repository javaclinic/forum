package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.JpaUserDao;
import example.forum.dao.UserDao;

public class AbstractUserServlet extends AbstractDaoServlet {

    private static final long serialVersionUID = 1L;

    protected UserDao dao;

    @Override
    public void init() throws ServletException {
        logger.debug("Initializing " + this.getClass().getSimpleName() + ".");
        dao = new JpaUserDao();
    }

}
