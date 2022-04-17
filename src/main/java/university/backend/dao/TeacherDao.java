package university.backend.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import university.backend.entities.Teacher;
import university.backend.util.HibernateUtil;

import java.util.List;

public class TeacherDao implements DaoInterface<Teacher, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    public TeacherDao() {

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

    public Long persist(Teacher entity) {
        return (Long) getCurrentSession().save(entity);
    }

    public void update(Teacher entity) {
        getCurrentSession().update(entity);
    }

    public Teacher findById(Long id) {
        return getCurrentSession().get(Teacher.class, id);
    }

    public void delete(Teacher entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Teacher> findAll() {
        return (List<Teacher>) getCurrentSession().createQuery("from Teacher").list();
    }

    public void deleteAll() {
        List<Teacher> entityList = findAll();
        for (Teacher entity : entityList) {
            delete(entity);
        }
    }
}