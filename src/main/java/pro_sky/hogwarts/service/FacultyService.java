package pro_sky.hogwarts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.dto.FacultyDto;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyDto createFaculty(Faculty faculty) {
        log.info("Was invoked method for create faculty - {}", faculty);
        var savedFaculty = facultyRepository.save(faculty);
        return convertDto(savedFaculty);
    }

    private FacultyDto convertDto(Faculty faculty) {
        var facultyDto = new FacultyDto();
        facultyDto.setId(faculty.getId());
        facultyDto.setName(facultyDto.getName());
        facultyDto.setColor(facultyDto.getColor());
        return facultyDto;
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        var facultyForUpdate = facultyRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Faculty this ID " + id + " not found";
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
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
        return facultyRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Faculty this ID " + id + " not found";
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    public List<Faculty> findAllFaculties() {
        log.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    public List<Faculty> findByNameOrColorContainingIgnoreCase(String query) {
        log.info("Was invoked method for find faculty by name or color");
        return facultyRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()) || e.getColor().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public Optional<String> getLongName() {
        log.info("Was invoked method for find faculty with the long name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));
    }
}
