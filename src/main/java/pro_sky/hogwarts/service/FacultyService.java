package pro_sky.hogwarts.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@Service
@Slf4j
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        log.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        var facultyForUpdate = facultyRepository.findById(id).orElseThrow(EntityExistsException::new);
        facultyForUpdate.setName(faculty.getName());
        facultyForUpdate.setColor(faculty.getColor());
        log.info("Was invoked method for edit faculty - {}", faculty);
        return facultyRepository.save(facultyForUpdate);
    }

    public void deleteFaculty(long id) {
        log.info("Was invoked method for delete faculty by id - {}", id);
        facultyRepository.deleteById(id);
    }

    public Faculty findFacultyById(long id) {
        log.info("Was invoked method for find faculty by id - {}", id);
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> findAll() {
        log.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColorContainingIgnoreCase(String query) {
        return facultyRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()) || e.getColor().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public Optional<String> getLongName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));
    }
}
