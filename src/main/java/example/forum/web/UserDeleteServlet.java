package example.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.forum.domain.User;

@WebServlet("/user_delete.html")
public class UserDeleteServlet extends AbstractUserServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing GET request.");
        try {
            String id = request.getParameter("id");
            if ( !isEmpty(id) ) {
                User user = dao.findById(new Long(id));
                logger.debug("Found user with id={} : {}", id, user);
                request.setAttribute("USER", user);
                dao.delete(user);
                logger.debug("Deleted user with id={} : {}", id, user);
                request.setAttribute("DELETED", true);
            }
            request.getRequestDispatcher("/WEB-INF/views/userView.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
