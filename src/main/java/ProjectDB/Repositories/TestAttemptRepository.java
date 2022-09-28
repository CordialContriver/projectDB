package ProjectDB.Repositories;

import ProjectDB.SessionFactoryProvider.SessionFactoryProvider;
import ProjectDB.Tables.Test;
import ProjectDB.Tables.TestAttempt;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class TestAttemptRepository {
    private final SessionFactory sessionFactory;
    public TestAttemptRepository() {
        sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
    }


    public void createTestAttempt(TestAttempt testAttempt) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(testAttempt);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TestAttempt> getAttemptListByTest(Test test) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from TestAttempt where test = :test",
                    TestAttempt.class).setParameter("test", test).list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<TestAttempt> getAttemptList() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from TestAttempt", TestAttempt.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public TestAttempt getTAByTest(Test test) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from TestAttempt where test = :test",
                    TestAttempt.class).setParameter("test", test).getSingleResult();
        } catch (NoResultException e){
            System.out.println("No result exception");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
