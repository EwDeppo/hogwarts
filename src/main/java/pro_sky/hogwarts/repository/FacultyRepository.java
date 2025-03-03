package pro_sky.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro_sky.hogwarts.entity.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
