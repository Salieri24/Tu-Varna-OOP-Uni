import university.dao.StudentDao;
import university.entities.Student;
import university.services.ExampleData;
import university.util.HibernateUtil;

public class Main {
    public static void main(String[] args){
        HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao();
        studentDao.persist(ExampleData.getStudent());
        assert !studentDao.findAll().isEmpty();
    }
}
