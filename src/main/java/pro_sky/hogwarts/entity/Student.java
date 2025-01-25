package pro_sky.hogwarts.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty-id")
    private Faculty faculty;
}
