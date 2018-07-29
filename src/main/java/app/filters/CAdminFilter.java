package app.filters;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import app.service.UserServiceImp;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Светлана on 29.07.2018.
 */
@WebFilter("/admin/*")
public class CAdminFilter implements Filter {
    private UserService userService = UserServiceImp.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User user = (User) req.getSession(true).getAttribute("currentUser");

        if (user == null){
            resp.sendError(401, "Unauthorized");
        } else if (user.getRole() != Role.admin){
            resp.sendError(401, "This page is only for admin");
        } else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
