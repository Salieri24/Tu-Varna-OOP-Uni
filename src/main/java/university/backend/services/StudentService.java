package university.backend.services;

import university.backend.dao.StudentDao;
import university.backend.entities.Student;

import java.util.List;

public class StudentService implements Service<Student> {
    private static StudentDao studentDao;

    public StudentService() {
        studentDao = new StudentDao();
    }

    public void persist(Student entity) {
        studentDao.openCurrentSessionWithTransaction();
        studentDao.persist(entity);
        studentDao.closeCurrentSessionWithTransaction();
    }

    public void update(Student entity) {
        studentDao.openCurrentSessionWithTransaction();
        studentDao.update(entity);
        studentDao.closeCurrentSessionWithTransaction();
    }

    public Student findById(Long id) {
        studentDao.openCurrentSession();
        Student student = studentDao.findById(id);
        studentDao.closeCurrentSession();
        return student;
    }

    public void delete(Long id) {
        studentDao.openCurrentSessionWithTransaction();
        Student student = studentDao.findById(id);
        studentDao.delete(student);
        studentDao.closeCurrentSessionWithTransaction();
    }

    public List<Student> findAll() {
        studentDao.openCurrentSession();
        List<Student> students = studentDao.findAll();
        studentDao.closeCurrentSession();
        return students;
    }

    public void deleteAll() {
        studentDao.openCurrentSessionWithTransaction();
        studentDao.deleteAll();
        studentDao.closeCurrentSessionWithTransaction();
    }

    public StudentDao studentDao() {
        return studentDao;
    }

    public void saveOrUpdate(Student student) {
        if (student.getId() != null && findById(student.getId()) != null)
            update(student);
        else persist(student);
    }
}