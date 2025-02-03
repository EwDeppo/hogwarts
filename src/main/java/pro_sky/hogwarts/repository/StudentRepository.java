package pro_sky.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro_sky.hogwarts.entity.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,
        Long> {
    Collection<Student> findStudentByAge(Long age);

    Collection<Student> findByAgeBetween(Long min, Long max);

    @Query(value = "SELECT COUNT(*) from student", nativeQuery = true)
    int getStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int getAverageAge();


    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
