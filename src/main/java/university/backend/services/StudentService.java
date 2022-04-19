package university.backend.services;

import university.backend.dao.StudentDao;
import university.backend.entities.Student;

import java.util.Arrays;
import java.util.List;

public class StudentService implements Service<Student> {
    private static StudentDao studentDao;

    private static final StudentService instance = new StudentService();

    public static StudentService getInstance() {
        return instance;
    }

    public StudentService() {
        studentDao = new StudentDao();
    }

    public Student persist(Student entity) {
        studentDao.openCurrentSessionWithTransaction();
        Long id = studentDao.persist(entity);
        studentDao.closeCurrentSessionWithTransaction();
        return findById(id);
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

    public List<Student> findAllInGroup(Long groupId) {
        studentDao.openCurrentSession();
        List<Student> students = studentDao.findAllInGroup(groupId);
        studentDao.closeCurrentSession();
        System.out.println("Got all students in group with id=" + groupId);
        System.out.println(Arrays.toString(students.toArray()));
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