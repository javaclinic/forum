package example.forum.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.forum.domain.Category;

@WebServlet("/category_delete.html")
public class CategoryDeleteServlet extends GenericCategoryServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing GET request.");
        try {
            String id = request.getParameter("id");
            if ( !isEmpty(id) ) {
                Category category = dao.findById(new Long(id));
                logger.debug("Found category with id={} : {}", id, category);
                request.setAttribute("CATEGORY", category);
                dao.delete(category);
                logger.debug("Deleted category with id={} : {}", id, category);
                request.setAttribute("DELETED", true);
            }
            request.getRequestDispatcher("/WEB-INF/views/categoryView.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
