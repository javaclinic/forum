package example.forum.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.forum.domain.Category;

@WebServlet({"/category_new.html","/category_edit.html"})
public class CategoryEditServlet extends GenericCategoryServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing GET request.");
        try {
            String id = request.getParameter("id");
            if ( !isEmpty(id) && request.getRequestURI().contains("edit") ) {
                Category category = dao.findById(new Long(id));
                logger.debug("Found category with id={} : {}", id, category);
                request.setAttribute("CATEGORY", category);
            }
            request.getRequestDispatcher("/WEB-INF/views/categoryForm.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing POST request.");
        try {

            List<String> errors = new LinkedList<>();
            Category category = new Category();

            // creating or updating user?
            String id = request.getParameter("id");
            if ( !isEmpty(id) && request.getRequestURI().contains("edit") ) {
                category.setId(new Long(id));
            }

            // required fields
            String name = request.getParameter("name");
            if ( isEmpty(name)) {
                errors.add("Name cannot be empty!");
            } else {
                category.setName(name);
            }

            // non-required fields
            category.setDescription(request.getParameter("description"));

            // any errors?
            if ( errors.size() == 0 ) {
                // create or update?
                if ( category.isIdSet() ) {
                    logger.debug("Updating existing category {}.", category);
                    category = dao.update(category);
                    request.setAttribute("UPDATED", true);
                } else {
                    logger.debug("Saving new category {}.", category);
                    category = dao.create(category);
                    request.setAttribute("CREATED", true);
                }
                request.setAttribute("CATEGORY", category);
                request.getRequestDispatcher("/WEB-INF/views/categoryView.jsp").forward(request, response);
            } else {
                logger.debug("Not saving category {}. Errors are present.", category);
                request.setAttribute("CATEGORY", category);
                request.setAttribute("ERRORS", errors);
                request.getRequestDispatcher("/WEB-INF/views/categoryForm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
