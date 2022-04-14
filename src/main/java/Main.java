import university.backend.entities.Student;
import university.backend.services.StudentService;
import university.backend.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();
        StudentService studentService = new StudentService();
        Student student = new Student("firstName", "lastName", LocalDate.now(), List.of());
        studentService.persist(student);
        List<Student> all = studentService.findAll();
        System.out.println(all.get(0).getFirstName());
    }
}