package university.backend.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import university.backend.entities.Student;
import university.backend.util.HibernateUtil;

import java.util.List;

@SuppressWarnings("ALL")
public class StudentDao implements DaoInterface<Student, Long> {
    private Session currentSession;

    private Transaction currentTransaction;

    public StudentDao() {

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

    public Long persist(Student entity) {
        return (Long) getCurrentSession().save(entity);
    }

    public void update(Student entity) {
        getCurrentSession().update(entity);
    }

    public Student findById(Long id) {
        return getCurrentSession().get(Student.class, id);
    }

    public void delete(Student entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Student> findAll() {
        return (List<Student>) getCurrentSession().createQuery("from Student").list();
    }

    public List<Student> findAllInGroup(Long groupId) {
        return (List<Student>) getCurrentSession().createNativeQuery("select s.* from student s where s.group_id = :uni_group",Student.class).setParameter("uni_group",groupId).list();
    }

    public void deleteAll() {
        List<Student> entityList = findAll();
        for (Student entity : entityList) {
            delete(entity);
        }
    }
}