package university.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private Integer points = 0;

    public Grades(String subjectName, Integer points) {
        this.subjectName = subjectName;
        this.points = points;
    }

    public void addPoints(int n) {
        if (points + n < 100)
            points += n;
        else points = 100;
    }

    @Override
    public String toString() {
        return "subjectName='" + subjectName + '\'' +
                ", points=" + points;
    }
}