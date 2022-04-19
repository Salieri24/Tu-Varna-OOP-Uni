package university.backend.services;

import university.backend.dao.GroupDao;
import university.backend.entities.Group;
import university.backend.entities.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupService implements Service<Group> {
    private static GroupDao groupDao;

    private static final GroupService instance = new GroupService();

    public static GroupService getInstance(){return instance;}

    public GroupService() {
        groupDao = new GroupDao();
    }

    public Group persist(Group entity) {
        groupDao.openCurrentSessionWithTransaction();
        Long id = groupDao.persist(entity);
        groupDao.closeCurrentSessionWithTransaction();
        return findById(id);
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

    public List<Group> findAllByUniversity(Long universityId){
        System.out.println("Finding all Groups within university with id =" + universityId);
        groupDao.openCurrentSession();
        List<Group> groups = groupDao.findAllByUniversity(universityId);
        groupDao.closeCurrentSession();
        System.out.println(Arrays.toString(groups.toArray()));
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
    public void saveOrUpdate(Group group) {
        if (group.getId() != null && findById(group.getId()) != null)
            update(group);
        else persist(group);
    }

    public List<Student> getAllStudents(Long universityId) {
        List<Student> students = new ArrayList<>();
        List<Group> groups = findAllByUniversity(universityId);
        for (Group group : groups)
            students.addAll(group.getStudents());
        return students;
    }

    public List<Student> getAllStudentsBySearch(Long id, String search) {
        groupDao.openCurrentSession();
        List<Student> students = groupDao.findAllByUniversityAndSearch(id,search);
        groupDao.closeCurrentSession();
        return students;
    }

    public List<Group> findByTeacherId(Long id) {
        if(id == null) return new ArrayList<>();
        groupDao.openCurrentSession();
        List<Group> groups = groupDao.findAllByTeacher(id);
        groupDao.closeCurrentSession();
        return groups;
    }
}