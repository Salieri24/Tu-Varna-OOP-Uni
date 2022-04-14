package university.backend.services;

import university.backend.dao.GradesDao;
import university.backend.entities.Grades;

import java.util.List;

public class GradesService implements Service<Grades> {
    private static GradesDao gradesDao;

    public GradesService() {
        gradesDao = new GradesDao();
    }

    public void persist(Grades entity) {
        gradesDao.openCurrentSessionWithTransaction();
        gradesDao.persist(entity);
        gradesDao.closeCurrentSessionWithTransaction();
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
        List<Grades> gradess = gradesDao.findAll();
        gradesDao.closeCurrentSession();
        return gradess;
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