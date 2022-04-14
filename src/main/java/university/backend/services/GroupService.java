package university.backend.services;

import university.backend.dao.GroupDao;
import university.backend.entities.Group;

import java.util.List;

public class GroupService implements Service<Group> {
    private static GroupDao groupDao;

    public GroupService() {
        groupDao = new GroupDao();
    }

    public void persist(Group entity) {
        groupDao.openCurrentSessionWithTransaction();
        groupDao.persist(entity);
        groupDao.closeCurrentSessionWithTransaction();
    }

    public void update(Group entity) {
        groupDao.openCurrentSessionWithTransaction();
        groupDao.update(entity);
        groupDao.closeCurrentSessionWithTransaction();
    }

    public Group findById(Long id) {
        groupDao.openCurrentSession();
        Group group = groupDao.findById(id);
        groupDao.closeCurrentSession();
        return group;
    }

    public void delete(Long id) {
        groupDao.openCurrentSessionWithTransaction();
        Group group = groupDao.findById(id);
        groupDao.delete(group);
        groupDao.closeCurrentSessionWithTransaction();
    }

    public List<Group> findAll() {
        groupDao.openCurrentSession();
        List<Group> groups = groupDao.findAll();
        groupDao.closeCurrentSession();
        return groups;
    }

    public void deleteAll() {
        groupDao.openCurrentSessionWithTransaction();
        groupDao.deleteAll();
        groupDao.closeCurrentSessionWithTransaction();
    }

    public GroupDao groupDao() {
        return groupDao;
    }
}