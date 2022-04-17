package university.backend.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    @Column(name = "group_name")
    private String name;
    @Column(name = "uni_id")
    private Long universityId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private List<Student> students = new ArrayList<>();
    @Override
    public String toString() {
        return "Name='" + name + '\'' +
                ", Students:" + students.size();
    }

    public Student findStudent(Long id) {
        for (Student student : this.getStudents()) {
            if (Objects.equals(student.getId(), id))
                return student;
        }
        return new Student();
    }
}