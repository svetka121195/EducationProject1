package app.service;

import app.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public interface UserService {

    long getUserId(String login);

    void addUser(User user);

    User getUser(long id);

    void deleteUser(long id);

    void updateUser(User newUser);

    List<User> getAllUsers();
}
