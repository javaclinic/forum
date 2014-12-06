package example.forum.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.forum.domain.Category;

@WebServlet("/categories.html")
public class CategoryListServlet extends GenericCategoryServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing GET request.");
        try {
            List<Category> categories = dao.findAll();
            logger.debug("Found all categories: {}", categories);
            request.setAttribute("CATEGORIES", categories);
            request.getRequestDispatcher("/WEB-INF/views/categoryList.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
