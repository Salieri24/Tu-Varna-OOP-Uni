package university.dao;

import university.entities.Student;

import java.util.List;

public class StudentDao extends HibernateSessionDAO implements DaoInterface<Student, Long> {

    @Override
    public void persist(Student entity) {
        openCurrentSession();
        getCurrentSession().save(entity);
        closeCurrentSession();
    }

    @Override
    public void update(Student entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Student findById(Long aLong) {
        return getCurrentSession().get(Student.class, aLong);
    }

    @Override
    public void delete(Student entity) {
        getCurrentSession().delete(entity);

    }

    @Override
    public List<Student> findAll() {
        List<Student> books;
        books = (List<Student>) getCurrentSession().createQuery("from Student").list();
        return books;
    }

    @Override
    public void deleteAll() {
        List<Student> entityList = findAll();
        for (Student entity : entityList) {
            delete(entity);
        }
    }
}
