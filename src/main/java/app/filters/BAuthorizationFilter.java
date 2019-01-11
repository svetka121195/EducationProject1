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
@WebFilter("/admin")
public class BAuthorizationFilter implements Filter {
	private UserService userService = UserServiceImp.getInstance();

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		User userFromSession = (User) req.getSession(true).getAttribute("currentUser");

		if (userFromSession == null){
			resp.sendError(401, "Unauthorized");
		}

		User userFromBD = userService.getUser(userFromSession.getId());

		if (userFromBD == null || !userFromSession.getLogin().equals(userFromBD.getLogin())
				|| !userFromSession.getPassword().equals(userFromBD.getPassword())) {
			resp.sendError(401, "Invalid current user");
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
