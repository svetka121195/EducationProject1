package app.servlets;


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

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    private UserService userService = UserServiceImp.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        req.setAttribute("usersList", userService.getAllUsers());

        resp.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        if (userService.getUserId(req.getParameter("login")) == -1) {
            User user = new User(req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("password"));

            userService.addUser(user);
            resp.sendRedirect("/list");
        } else {
            req.setAttribute("error", "login is already used");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/error");
            requestDispatcher.forward(req, resp);
        }
    }
}
