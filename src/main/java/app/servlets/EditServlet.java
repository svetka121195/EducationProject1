package app.servlets;

import app.model.Role;
import app.service.UserService;
import app.model.User;
import app.service.UserServiceImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private UserService userService = UserServiceImp.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        long id = Long.parseLong(req.getParameter("id"));

        if (userService.getUser(id) == null){
            req.setAttribute("error", "id for edit is not found");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/error");
            requestDispatcher.forward(req, resp);
        } else {
            req.setAttribute("user", userService.getUser(id));

            resp.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/edit.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        long id = Long.parseLong(req.getParameter("id"));

        if (userService.getUser(id) == null){
            req.setAttribute("error", "id is not found");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/error");
            requestDispatcher.forward(req, resp);
        } else {
            User user = new User(req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("password"),
                    Role.valueOf(req.getParameter("role")));
            user.setId(id);

            userService.updateUser(user);

            resp.sendRedirect("/admin");
        }
    }
}
