package app.dao;

import app.exception.DBException;
import app.model.User;

import java.util.List;

/**
 * Created by Светлана on 16.07.2018.
 */
public interface UsersDao {
    long getUserId(String login) throws DBException;

    void addUser(User user) throws DBException;

    User getUserByLogin(String login) throws DBException;

    User getUserById(long id) throws DBException;

    void deleteUser(long id) throws DBException;

    void updateUser(User user) throws DBException;

    List<User> getAllUsers() throws DBException;

    /*void createTable() throws DBException;

    void dropTable() throws DBException;*/
}


