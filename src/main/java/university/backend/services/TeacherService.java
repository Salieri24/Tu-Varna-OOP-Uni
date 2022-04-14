package university.backend.services;

import university.backend.dao.TeacherDao;
import university.backend.entities.Teacher;

import java.util.List;

public class TeacherService implements Service<Teacher> {
    private static TeacherDao teacherDao;

    public TeacherService() {
        teacherDao = new TeacherDao();
    }

    public void persist(Teacher entity) {
        teacherDao.openCurrentSessionWithTransaction();
        teacherDao.persist(entity);
        teacherDao.closeCurrentSessionWithTransaction();
    }

    public void update(Teacher entity) {
        teacherDao.openCurrentSessionWithTransaction();
        teacherDao.update(entity);
        teacherDao.closeCurrentSessionWithTransaction();
    }

    public Teacher findById(Long id) {
        teacherDao.openCurrentSession();
        Teacher teacher = teacherDao.findById(id);
        teacherDao.closeCurrentSession();
        return teacher;
    }

    public void delete(Long id) {
        teacherDao.openCurrentSessionWithTransaction();
        Teacher teacher = teacherDao.findById(id);
        teacherDao.delete(teacher);
        teacherDao.closeCurrentSessionWithTransaction();
    }

    public List<Teacher> findAll() {
        teacherDao.openCurrentSession();
        List<Teacher> teachers = teacherDao.findAll();
        teacherDao.closeCurrentSession();
        return teachers;
    }

    public void deleteAll() {
        teacherDao.openCurrentSessionWithTransaction();
        teacherDao.deleteAll();
        teacherDao.closeCurrentSessionWithTransaction();
    }

    public TeacherDao teacherDao() {
        return teacherDao;
    }
}