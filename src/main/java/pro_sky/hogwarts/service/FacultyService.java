package pro_sky.hogwarts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty - {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty by id - {}", id);
        facultyRepository.deleteById(id);
    }

    public Faculty findFacultyById(long id) {
        logger.info("Was invoked method for find faculty by id - {}", id);
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> findAll() {
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String query) {
        return facultyRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase())
                || e.getColor().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public String getLongName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String :: length)).get();
    }

    public Optional getLongName2() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));
    }
}
