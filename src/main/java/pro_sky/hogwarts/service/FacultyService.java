package pro_sky.hogwarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.repository.FacultyRepositiry;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepositiry facultyRepositiry;

    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyRepositiry.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepositiry.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepositiry.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepositiry.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepositiry.findAll();
    }

    public List<Faculty> findByColor(String color) {
        return getAllFaculty().stream()
                .filter(e -> Objects.equals(e.getColor(), color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> findFacultyByName(String name) {
        return facultyRepositiry.findFacultyByNameContainsIgnoreCase(name);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepositiry.findFacultyByColorContainsIgnoreCase(color);
    }
}
