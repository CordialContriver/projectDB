package ProjectDB.Repositories;

import ProjectDB.SessionFactoryProvider.SessionFactoryProvider;
import ProjectDB.Tables.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class UserRepository {
    private final SessionFactory sessionFactory;
    public UserRepository() {
        sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
    }

    public void createUser(User user) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User where username = :username",
                    User.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e){
            System.out.println("No result exception");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            System.out.println("Vartotojas ištrintas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAdminCount() {
        try (Session session = sessionFactory.openSession()) {
            return (int)session.createQuery("select count(*) from users where usertype = :usertype")
                    .setParameter("usertype","ADMIN").stream().count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
