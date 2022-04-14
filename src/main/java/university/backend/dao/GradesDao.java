package university.backend.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import university.backend.entities.Grades;
import university.backend.util.HibernateUtil;

import java.util.List;

public class GradesDao implements DaoInterface<Grades, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    public GradesDao() {

    }

    public Session openCurrentSession() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Grades entity) {
        getCurrentSession().save(entity);
    }

    public void update(Grades entity) {
        getCurrentSession().update(entity);
    }

    public Grades findById(Long id) {
        return getCurrentSession().get(Grades.class, id);
    }

    public void delete(Grades entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Grades> findAll() {
        return (List<Grades>) getCurrentSession().createQuery("from Grades").list();
    }

    public void deleteAll() {
        List<Grades> entityList = findAll();
        for (Grades entity : entityList) {
            delete(entity);
        }
    }
}