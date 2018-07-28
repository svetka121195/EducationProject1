package app.service;

import app.dao.UserDaoFactory;
import app.dao.UsersDao;
import app.exception.DBException;
import app.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public final class UserServiceImp implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImp.class);
    private static volatile UserService instance;
    private static final UsersDao dao = UserDaoFactory.getUsersDao();


    private UserServiceImp() {
    }

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserServiceImp();
                }
            }
        }
        return localInstance;
    }


    public long getUserId(String login) {
        long id = -1;
        try {
            id = dao.getUserId(login);
        } catch (DBException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        }
        return id;
    }

    public void addUser(User user) {
        try {
            dao.addUser(user);
        } catch (DBException e) {
            LOGGER.warn(e);
        }
    }

    public User getUser(long id) {
        User user = null;
        try {
            user = dao.getUserById(id);
        } catch (DBException e) {
            LOGGER.warn(e);
        }
        return user;
    }

    public void deleteUser(long id) {
        try {
            dao.deleteUser(id);
        } catch (DBException e) {
            LOGGER.warn(e);
        }
    }

    public void updateUser(User newUser) {
        try {
            dao.updateUser(newUser);
        } catch (DBException e) {
            LOGGER.warn(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try {
            list = dao.getAllUsers();
        } catch (DBException e) {
            LOGGER.warn(e);
        }
        return list;
    }
}
