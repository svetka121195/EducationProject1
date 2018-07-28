package app.dao;

import app.exception.DBException;
import app.model.User;
import app.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoJDBC implements UsersDao {

    private Executor executor;
    private static Connection connection;

    public UsersDaoJDBC(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }

    @Override
    public long getUserId(String login) throws DBException {
        long id = -1;
        try {
            id = executor.execQuery("select * from users where user_login='" + login + "'", result -> {
                long i = -1;
                if (result.next()) {
                    i = result.getLong(1);
                }
                return i;
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return id;
    }

    @Override
    public void addUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            executor.execUpdate("insert into users (user_name,  user_login, user_password ) values ('"
                    + user.getName() + "', " + "'" + user.getLogin() + "', '" + user.getPassword() + "')");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        User user = null;
        try {
            user = executor.execQuery("select * from users where user_login = \"" + login + "\"", result -> {
                User u = null;
                if (result.next()) {
                    u = new User(result.getString(2),
                            result.getString(3),
                            result.getString(4));

                    u.setId(result.getInt(1));
                }
                return u;
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public User getUserById(long id) throws DBException {
        User user = null;
        try {
            user = executor.execQuery("select * from users where id = \"" + id + "\"", result -> {
                User u = null;
                if (result.next()) {
                    u = new User(result.getString(2),
                            result.getString(3),
                            result.getString(4));

                    u.setId(result.getInt(1));
                }
                return u;
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public void deleteUser(long id) throws DBException {
        try {
            connection.setAutoCommit(false);
            executor.execUpdate("delete from users where  id = \"" + id + "\"");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public void updateUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            executor.execUpdate("UPDATE users SET " +
                    "user_name =\"" + user.getName() + "\", " +
                    "user_login =\"" + user.getLogin() + "\", " +
                    "user_password =\"" + user.getPassword() + "\" " +
                    "WHERE id =\"" + user.getId() + "\"");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public List<User> getAllUsers() throws DBException {
        List<User> list = null;
        try {
            list = executor.execQuery("select * from users", result -> {
                List<User> users = new ArrayList<>();
                while (result.next()) {
                    User user = new User(result.getString(2), result.getString(3), result.getString(4));
                    user.setId(result.getInt(1));
                    users.add(user);
                }
                return users;
            });
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }


    public void createTable() throws DBException {
        try {
            executor.execUpdate("create table if not exists users " +
                    "(id bigint auto_increment, user_name varchar(256), " +
                    "user_login varchar(256) UNIQUE, user_password varchar(256), " +
                    "primary key (id)) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    public void dropTable() throws DBException {
        try {
            executor.execUpdate("drop table users");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
