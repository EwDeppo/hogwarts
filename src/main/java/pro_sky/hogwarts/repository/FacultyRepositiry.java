package pro_sky.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro_sky.hogwarts.entity.Faculty;

import java.util.Collection;

@Repository
public interface FacultyRepositiry extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findFacultyByNameContainsIgnoreCase(String name);

    Collection<Faculty> findFacultyByColorContainsIgnoreCase(String color);
}
