package pro_sky.hogwarts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("{id}/faculty")
    public Faculty editFaculty(@PathVariable Long id,
                                               @RequestBody Faculty faculty) {
        return facultyService.editFaculty(id, faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<Faculty>> findAll(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String color) {
        if (name != null) {
            return ResponseEntity.ok(facultyService.findByNameOrColorContainingIgnoreCase(name));
        }
        if (color != null) {
            return ResponseEntity.ok(facultyService.findByNameOrColorContainingIgnoreCase(color));
        }
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping("/longName")
    public Optional<String> getLongName() {
        return facultyService.getLongName();
    }
}
