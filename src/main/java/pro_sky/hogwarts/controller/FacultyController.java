package pro_sky.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro_sky.hogwarts.dto.FacultyDto;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @Operation(summary = "Добавление факультета")
    @PostMapping
    public ResponseEntity<FacultyDto> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @Operation(summary = "Поиск факультета по ID")
    @GetMapping("{id}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findFacultyById(id));
    }

    @Operation(summary = "Обновление факультета")
    @PutMapping("{id}")
    public ResponseEntity<FacultyDto> editFaculty(@PathVariable Long id,
                                                  @RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.editFaculty(id, faculty));
    }

    @Operation(summary = "Удаление студента")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Поиск всех факультетов")
    @GetMapping
    public ResponseEntity<List<FacultyDto>> findAll() {
        return ResponseEntity.ok(facultyService.findAllFaculties());
    }

    @Operation(summary = "Поиск факултетеа по цвету или имени")
    @GetMapping("{nameOrColor}")
    public ResponseEntity<List<FacultyDto>> getFacultyByNameOrColor(@PathVariable String nameOrColor) {
        return ResponseEntity.ok(facultyService.findByNameOrColorContainingIgnoreCase(nameOrColor));
    }

    @GetMapping("/longName")
    public ResponseEntity<Optional<String>> getLongName() {
        return ResponseEntity.ok(facultyService.getLongName());
    }
}
