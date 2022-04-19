package university.backend.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private List<Group> groups = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="subjects", joinColumns=@JoinColumn(name="university_id"))
    @Column(name="subject",unique = true,nullable = false)
    private List<String> subjects = new ArrayList<>();

    public University(String name) {
        this.name = name;
    }

    public void addSubject(String subject){
        subjects.add(subject);
    }

    @Override
    public String toString() {
        return this.name;
    }
}