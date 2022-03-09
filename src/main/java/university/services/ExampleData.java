package university.services;

import university.entities.Student;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class ExampleData {
    private static Long studentId = 0L;

    public static Student getStudent() {
        return new Student(studentId++, "randomName" + studentId, "RandomName" + studentId, Date.from(Instant.now()), new ArrayList<>());
    }
}
