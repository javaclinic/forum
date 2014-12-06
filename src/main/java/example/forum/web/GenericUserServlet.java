package example.forum.web;

import javax.servlet.ServletException;

import example.forum.dao.UserDao;

/**
 * GenericUserServlet is a generic DAO servlet for all User-related DAO servlets.
 * 
 * @author nevenc
 *
 */
public class GenericUserServlet extends GenericDaoServlet {

    private static final long serialVersionUID = 1L;
    protected UserDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = springContext.getBean(UserDao.class);
    }

}
