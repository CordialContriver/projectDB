package ProjectDB.Repositories;

import ProjectDB.SessionFactoryProvider.SessionFactoryProvider;
import ProjectDB.Tables.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class AnswerRepository {
    private final SessionFactory sessionFactory;

    public AnswerRepository() {
        sessionFactory = SessionFactoryProvider.getInstance().getSessionFactory();
    }

    public void createNewAnswer(Answer answer) {
        Transaction transaction;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(answer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Answer> getAnswerList() {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("from Answer", Answer.class).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }

}
