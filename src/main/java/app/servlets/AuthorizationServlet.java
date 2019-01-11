package app.servlets;


import app.model.Role;
import app.model.User;
import app.service.UserService;
import app.service.UserServiceImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Authorization")
public class AuthorizationServlet extends HttpServlet {
    private UserService userService = UserServiceImp.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
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
