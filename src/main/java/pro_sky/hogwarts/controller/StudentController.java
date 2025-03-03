package pro_sky.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro_sky.hogwarts.entity.Avatar;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.dto.StudentDto;
import pro_sky.hogwarts.service.AvatarService;
import pro_sky.hogwarts.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;
    private final AvatarService avatarService;

    @Operation(summary = "Добавление студента")
    @PostMapping
    public StudentDto createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @Operation(summary = "Поиск студента по ID")
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    @Operation(summary = "Обновление студента")
    @PutMapping("{id}")
    public ResponseEntity<Student> editStudent(@PathVariable Long id,
                                               @RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(id, student));
    }

    @Operation(summary = "Удаление студента")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Поиск всех студентов")
    @GetMapping
    public ResponseEntity<List<Student>> findStudents() {
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @Operation(summary = "Поиск студентов по возрасту")
    @GetMapping("{age}")
    public ResponseEntity<List<Student>> findStudentsByAge(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findStudentsByAge(id));
    }

    @Operation(summary = "Поиск всех студентов")
    @GetMapping("{min}/{max}")
    public ResponseEntity<List<Student>> findStudentsByAgeBetween(@PathVariable Long min,
                                                                  @PathVariable Long max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @Operation(summary = "Добавление аватара")
    @PostMapping(value = "/{student_id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long student_id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(student_id, avatar);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatarPreview(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatarPreview(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Integer> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping(value = "/averageAge")
    public ResponseEntity<Integer> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping(value = "/lastFive")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<List<Student>> findStudentsByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.findStudentsByName(name));
    }

    @GetMapping("startA")
    public ResponseEntity<Collection<String>> findAllStudentsStartWithA() {
        return ResponseEntity.ok(studentService.findAllStudentsStartWithA());
    }

    @GetMapping("/getAverageAge")
    public Double getStudentAverageAge() {
        return studentService.getStudentAverageAge();
    }
}

