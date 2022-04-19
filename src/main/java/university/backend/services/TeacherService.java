package university.backend.services;

import university.backend.dao.TeacherDao;
import university.backend.entities.Group;
import university.backend.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherService implements Service<Teacher> {
    private static TeacherDao teacherDao;

    private static final TeacherService instance = new TeacherService();

    public static TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public TeacherService() {
        teacherDao = new TeacherDao();
    }

    public static TeacherService getInstance() {
        return instance;
    }

    public Teacher persist(Teacher entity) {
        teacherDao.openCurrentSessionWithTransaction();
        Long id = teacherDao.persist(entity);
        teacherDao.closeCurrentSessionWithTransaction();
        return findById(id);
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

    public void saveOrUpdate(Teacher teacher) {
        if(teacher.getId()!=null) update(teacher);
        else persist(teacher);
    }
}