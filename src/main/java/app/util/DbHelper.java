package app.util;

import app.dao.UsersDaoHibernate;
import app.dao.UsersDaoJDBC;
import app.model.User;
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
public class DbHelper {
    private static final Logger LOGGER = LogManager.getLogger(DbHelper.class);

    public static UsersDaoJDBC getUsersDaoJDBC(){
        return new UsersDaoJDBC(getMysqlConnection());
    }

    public static UsersDaoHibernate getUsersDaoHibernate(){
        return new UsersDaoHibernate(createSessionFactory(getMySqlConfiguration()));
    }


    @SuppressWarnings("UnusedDeclaration")
    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();

            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Properties property = new Properties();
                property.load(classLoader.getResourceAsStream("JDBC.properties"));

                url.
                        append(property.getProperty("db.type")).               //db type
                        append(property.getProperty("db.host")).               //host name
                        append(property.getProperty("db.port")).               //port
                        append(property.getProperty("db.name")).               //db name
                        append(property.getProperty("db.useUnicode")).
                        append(property.getProperty("db.characterEncoding")).
                        append(property.getProperty("db.login")).               //login
                        append(property.getProperty("db.password"));            //password

                LOGGER.info("URL: " + url + "\n");

            } catch (IOException e) {
                LOGGER.error("ERROR: file with properties JDBC is absent", e);
            }

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("UnusedDeclaration")
    private static Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Properties property = new Properties();
            property.load(classLoader.getResourceAsStream("hibernate.properties"));

            configuration.setProperty("hibernate.dialect", property.getProperty("h.dialect"));
            configuration.setProperty("hibernate.connection.driver_class", property.getProperty("h.driver_class"));
            configuration.setProperty("hibernate.connection.url", property.getProperty("h.url"));
            configuration.setProperty("hibernate.connection.characterEncoding", property.getProperty("h.characterEncoding"));
            configuration.setProperty("hibernate.connection.username", property.getProperty("h.username"));
            configuration.setProperty("hibernate.connection.password", property.getProperty("h.password"));
            configuration.setProperty("hibernate.show_sql", property.getProperty("h.hibernate.show_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", property.getProperty("h.hbm2ddl.auto"));
            LOGGER.info("Hibernate");
        } catch (IOException e) {
            LOGGER.error("ERROR: file with properties hibernate is absent", e);
        }
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
