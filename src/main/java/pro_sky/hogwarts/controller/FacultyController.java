package pro_sky.hogwarts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping //GET http://localhost:8080/faculty
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping //PUT http://localhost:8080/faculty
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}") //DELETE http://localhost:8080/faculty/12
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam("nameOrColor") String nameOrColor) {
        return ResponseEntity.ok(facultyService.findByName(nameOrColor));
    }

    @GetMapping //POST http://localhost:8080/faculty/12
    public ResponseEntity findFaculties(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String color) {
        if (name != null && name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByName(name));
        }
        if (color != null && color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(facultyService.findAllFaculty());
    }
}
