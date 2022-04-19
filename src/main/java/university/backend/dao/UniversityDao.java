package university.backend.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import university.backend.entities.University;
import university.backend.util.HibernateUtil;

import java.util.Collection;
import java.util.List;

public class UniversityDao implements DaoInterface<University, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    public UniversityDao() {

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

    public Long persist(University entity) {
        return (Long) getCurrentSession().save(entity);
    }

    public void update(University entity) {
        getCurrentSession().update(entity);
    }

    public University findById(Long id) {
        return getCurrentSession().get(University.class, id);
    }

    public void delete(University entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<University> findAll() {
        return (List<University>) getCurrentSession().createQuery("from University").list();
    }

    public void deleteAll() {
        List<University> entityList = findAll();
        for (University entity : entityList) {
            delete(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public List<University> findAllByName(String searchText) {
        return (List<University>) getCurrentSession().createQuery("from University u where u.name like :uniName").setParameter("uniName","%"+searchText+"%").list();
    }

    public List<String> getAllSubjects(Long id) {
        return getCurrentSession().createNativeQuery("select s.subject from subjects s where s.university_id = :id")
                .setParameter("id",id).list();
    }
}