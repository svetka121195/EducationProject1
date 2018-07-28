package app.dao;

import app.model.User;
import app.util.DbHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Светлана on 27.07.2018.
 */
public class UserDaoFactory {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoFactory.class);

    public static UsersDao getUsersDao() {
        String name = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Properties property = new Properties();
            property.load(classLoader.getResourceAsStream("config.properties"));
            name = property.getProperty("db.access");
        } catch (IOException e) {
            LOGGER.error("ERROR: file with properties is absent", e);
        }

        LOGGER.info("UserService use: " + name);

        switch (name) {
            case "JDBC": {
                return DbHelper.getUsersDaoJDBC();
            }
            case "Hibernate": {
                return DbHelper.getUsersDaoHibernate();
            }
            default: {
                return null;
            }
        }
    }



}
