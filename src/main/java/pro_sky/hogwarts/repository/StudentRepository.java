package pro_sky.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro_sky.hogwarts.entity.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int minAge, int maxAge);
}
