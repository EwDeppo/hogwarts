package pro_sky.hogwarts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue
    private String id;
}
