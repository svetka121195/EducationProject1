package app.filters;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import app.service.UserServiceImp;
import app.util.DbHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Светлана on 29.07.2018.
 */
@WebFilter("/Authorization")
public class BAuthorizationFilter implements Filter {
    private UserService userService = UserServiceImp.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String login = (String) req.getParameter("login");
        String password = (String) req.getParameter("password");
        Long id = userService.getUserId(login);


        if (id == -1) {
            resp.sendError(401, "Login is not found");
        } else {
            User user = userService.getUser(id);
            if (password.equals(user.getPassword())) {
                req.getSession().setAttribute("currentUser", user);
                if (user.getRole() == Role.admin) {
                    resp.sendRedirect("/admin");
                } else {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/user.jsp");
                    requestDispatcher.forward(req, resp);
                }
            } else {
                resp.sendError(401, "Wrong password");
            }
        }
    }
}
