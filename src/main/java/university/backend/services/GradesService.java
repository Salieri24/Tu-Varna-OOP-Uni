package university.backend.services;

import university.backend.dao.GradesDao;
import university.backend.entities.Grades;

import java.util.List;

public class GradesService implements Service<Grades> {
    private static GradesDao gradesDao;

    private static final GradesService instance = new GradesService();

    public static GradesService getInstance(){return instance;}

    public GradesService() {
        gradesDao = new GradesDao();
    }

    public Grades persist(Grades entity) {
        gradesDao.openCurrentSessionWithTransaction();
        Long l = gradesDao.persist(entity);
        gradesDao.closeCurrentSessionWithTransaction();
        return findById(l);
    }

    public void update(Grades entity) {
        gradesDao.openCurrentSessionWithTransaction();
        gradesDao.update(entity);
        gradesDao.closeCurrentSessionWithTransaction();
    }

    public Grades findById(Long id) {
        gradesDao.openCurrentSession();
        Grades grades = gradesDao.findById(id);
        gradesDao.closeCurrentSession();
        return grades;
    }

    public void delete(Long id) {
        gradesDao.openCurrentSessionWithTransaction();
        Grades grades = gradesDao.findById(id);
        gradesDao.delete(grades);
        gradesDao.closeCurrentSessionWithTransaction();
    }

    public List<Grades> findAll() {
        gradesDao.openCurrentSession();
        List<Grades> grades = gradesDao.findAll();
        gradesDao.closeCurrentSession();
        return grades;
    }

    public List<Grades> findAllForStudent(Long id) {
        gradesDao.openCurrentSession();
        List<Grades> grades = gradesDao.findAllByStudentId(id);
        gradesDao.closeCurrentSession();
        return grades;
    }

    public void deleteAll() {
        gradesDao.openCurrentSessionWithTransaction();
        gradesDao.deleteAll();
        gradesDao.closeCurrentSessionWithTransaction();
    }

    public GradesDao gradesDao() {
        return gradesDao;
    }
}