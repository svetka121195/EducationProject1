package app.servlets;

import app.service.UserService;
import app.service.UserServiceImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Светлана on 15.07.2018.
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private UserService userService = UserServiceImp.getInstance();

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
            userService.deleteUser(id);
            resp.sendRedirect("/list");
        }
    }
}
