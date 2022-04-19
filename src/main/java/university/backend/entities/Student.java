package university.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String facultyNum;
    private LocalDate dateOfBirth;
    private Long groupId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Set<Grades> gradesList = new HashSet<>();

    public Student(String firstName, String lastName, LocalDate dateOfBirth, String facultyNum, Set<Grades> gradesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.facultyNum = facultyNum;
        this.gradesList = gradesList;
    }

    public Student(String first, String last, LocalDate dob, String fac) {
        this.firstName = first;
        this.lastName = last;
        this.dateOfBirth = dob;
        this.facultyNum = fac;
    }

    @Override
    public String toString() {
        String s = firstName + "  " + lastName;
        return s.toUpperCase(Locale.ROOT);
    }
}