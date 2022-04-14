package university.backend.services;

import university.backend.dao.UniversityDao;
import university.backend.entities.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityService implements Service<University> {
    private static UniversityDao universityDao;

    public UniversityService() {
        universityDao = new UniversityDao();
    }

    public void persist(University entity) {
        universityDao.openCurrentSessionWithTransaction();
        universityDao.persist(entity);
        universityDao.closeCurrentSessionWithTransaction();
    }

    public void update(University entity) {
        universityDao.openCurrentSessionWithTransaction();
        universityDao.update(entity);
        universityDao.closeCurrentSessionWithTransaction();
    }

    public University findById(Long id) {
        universityDao.openCurrentSession();
        University university = universityDao.findById(id);
        universityDao.closeCurrentSession();
        return university;
    }

    public void delete(Long id) {
        universityDao.openCurrentSessionWithTransaction();
        University university = universityDao.findById(id);
        universityDao.delete(university);
        universityDao.closeCurrentSessionWithTransaction();
    }

    public List<University> findAll() {
        universityDao.openCurrentSession();
        List<University> all = universityDao.findAll();
        universityDao.closeCurrentSession();
        return (all != null) ? all : new ArrayList<>();
    }

    public void deleteAll() {
        universityDao.openCurrentSessionWithTransaction();
        universityDao.deleteAll();
        universityDao.closeCurrentSessionWithTransaction();
    }

    public UniversityDao universityDao() {
        return universityDao;
    }

    public void saveOrUpdate(University university) {
        if (university.getId() != null) update(university);
        else persist(university);
    }
}