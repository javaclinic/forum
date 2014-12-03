package example.forum.web;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.forum.domain.User;

@WebServlet({"/user_new.html","/user_edit.html"})
public class UserEditServlet extends GenericUserServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Processing GET request.");
        try {
            String id = request.getParameter("id");
            if ( !isEmpty(id) && request.getRequestURI().contains("edit") ) {
                User user = dao.findById(new Long(id));
                logger.debug("Found user with id={} : {}", id, user);
                request.setAttribute("USER", user);
                request.getSession().setAttribute("USER_TO_EDIT", user);
            }
            request.getRequestDispatcher("/WEB-INF/views/userForm.jsp").forward(request, response);
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

            User user = (User) request.getSession().getAttribute("USER_TO_EDIT");
            if ( user == null ) {
                user = new User();
                user.setCreated(new Date());
            }

            // creating or updating user?
            String id = request.getParameter("id");
            if ( !isEmpty(id) && request.getRequestURI().contains("edit") ) {
                user.setId(new Long(id));
            }

            // required fields
            String firstname = request.getParameter("firstname");
            if ( isEmpty(firstname)) {
                errors.add("First name cannot be empty!");
            } else {
                user.setFirstname(firstname);
            }
            String lastname = request.getParameter("lastname");
            if ( isEmpty(lastname)) {
                errors.add("Last name cannot be empty!");
            } else {
                user.setLastname(lastname);
            }
            String email = request.getParameter("email");
            if ( isEmpty(email)) {
                errors.add("Email cannot be empty!");
            } else {
                user.setEmail(email);
            }

            // non-required fields
            user.setOrganization(request.getParameter("organization"));
            user.setTitle(request.getParameter("title"));

            // passwords
            String password = request.getParameter("password");
            String passwordVerification = request.getParameter("password_verification");
            if ( isEmpty(password) ) {
                if ( !user.isIdSet() || !isEmpty(passwordVerification) ) {
                    errors.add("Password cannot be empty!");
                }
            } else if ( isEmpty(passwordVerification) ) {
                if ( !user.isIdSet() || !isEmpty(password)) {
                    errors.add("Password verification cannot be empty!");
                }
            } else if ( ! password.equals(passwordVerification) ) {
                errors.add("Passwords do not match!");
            } else {
                user.setPassword(password);
            }

            // any errors?
            if ( errors.size() == 0 ) {
                // create or update?
                if ( user.isIdSet() ) {
                    logger.debug("Updating existing user {}.", user);
                    user = dao.update(user);
                    request.setAttribute("UPDATED", true);
                } else {
                    logger.debug("Saving new user {}.", user);
                    user = dao.create(user);
                    request.setAttribute("CREATED", true);
                }
                request.setAttribute("USER", user);
                request.getSession().removeAttribute("USER_TO_EDIT");
                request.getRequestDispatcher("/WEB-INF/views/userView.jsp").forward(request, response);
            } else {
                logger.debug("Not saving user {}. Errors are present.", user);
                request.setAttribute("USER", user);
                request.setAttribute("ERRORS", errors);
                request.getRequestDispatcher("/WEB-INF/views/userForm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("ERROR", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
