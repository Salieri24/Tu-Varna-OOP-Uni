package university.backend.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import university.backend.entities.Group;
import university.backend.util.HibernateUtil;

import java.util.List;


public class GroupDao implements DaoInterface<Group, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    public GroupDao() {

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

    public Long persist(Group entity) {
        return (Long) getCurrentSession().save(entity);
    }

    public void update(Group entity) {
        getCurrentSession().update(entity);
    }

    public Group findById(Long id) {
        return getCurrentSession().get(Group.class, id);
    }

    public void delete(Group entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Group> findAll() {
        return (List<Group>) getCurrentSession().createQuery("from Group").list();
    }

    public void deleteAll() {
        List<Group> entityList = findAll();
        for (Group entity : entityList) {
            delete(entity);
        }
    }

    public List<Group> findAllByUniversity(Long universityId) {
        return getCurrentSession().createNativeQuery("select g.* from group_table g where g.uni_id = :uni_id", Group.class)
                .setParameter("uni_id", universityId).list();
    }
}