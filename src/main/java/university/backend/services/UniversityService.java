package university.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.common.util.impl.Log;
import org.jboss.logging.Logger;
import university.backend.dao.UniversityDao;
import university.backend.entities.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityService implements Service<University> {
    private static UniversityDao universityDao;

    private static final UniversityService instance = new UniversityService();

    public static UniversityDao getUniversityDao() {
        return universityDao;
    }

    public UniversityService() {
        universityDao = new UniversityDao();
    }

    public University persist(University entity) {
        universityDao.openCurrentSessionWithTransaction();
        Long id = universityDao.persist(entity);
        universityDao.closeCurrentSessionWithTransaction();
        System.out.println("Saved university with id ="+id);
        return findById(id);
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

    public List<University> findAllByName(String searchText) {
        universityDao.openCurrentSession();
        List<University> all = universityDao.findAllByName(searchText);
        universityDao.closeCurrentSession();
        return all;
    }
}