package ProjectDB.SessionFactoryProvider;

import ProjectDB.Tables.*;
import ProjectDB.Utilities.UserType;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

public class SessionFactoryProvider {

    private static SessionFactoryProvider instance;
    private final SessionFactory sessionFactory;

    private SessionFactoryProvider() {
        Configuration configuration = new Configuration();
        configuration.setProperties(createHibernateProperties());

        configuration.addAnnotatedClass(Answer.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(TestAttempt.class);
        configuration.addAnnotatedClass(Test.class);
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryProvider getInstance() {
        if (instance == null) {
            instance = new SessionFactoryProvider();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private Properties createHibernateProperties() {
        Properties p = new Properties();

        p.put(DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        p.put(DRIVER, "org.postgresql.Driver");
        p.put(URL, "jdbc:postgresql://localhost/projectdb2");
        p.put(USER, "postgres");
        p.put(PASS, "java");
        p.put(HBM2DDL_AUTO, "update");

        return p;
    }
}
