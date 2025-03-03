package pro_sky.hogwarts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Setter
@Getter
@Entity
@JsonIgnoreProperties(value = "students")
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Collection<Student> students;
}
