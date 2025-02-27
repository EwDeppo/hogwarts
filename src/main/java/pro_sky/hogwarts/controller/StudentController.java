package pro_sky.hogwarts.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro_sky.hogwarts.entity.Avatar;
import pro_sky.hogwarts.entity.Student;
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
public class StudentController {


    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @Operation(summary = "Добавление студента")
    @PostMapping("/add")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @Operation(summary = "Поиск студента по ID")
    @GetMapping("/get/{id}") // GET http://localhost:8080/student/59
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @Operation(summary = "Обновление студента")
    @PutMapping("/update/{id}/student") //PUT http://localhost:8080/student
    public ResponseEntity<Student> editStudent(@PathVariable Long id,
                                               @RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(id, student));
    }

    @Operation(summary = "Удаление студента")
    @DeleteMapping("/delete/{id}") //DELETE http://localhost:8080/student/59
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Поиск всех студентов/по возрасту/по возрасту в промежутке между")
    @GetMapping("/get")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Long age,
                                                            @RequestParam(required = false) Long min,
                                                            @RequestParam(required = false) Long max) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentsByAge(age));
        }
        if (min != null && max != null && min < max) {
            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }
        return ResponseEntity.ok(studentService.findAllStudents());
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
        Integer count = studentService.getStudents();
        return ResponseEntity.ok(count);
    }

    @GetMapping(value = "/averageAge")
    public ResponseEntity<Integer> getAverageAge() {
        Integer average = studentService.getAverageAge();
        return ResponseEntity.ok(average);
    }

    @GetMapping(value = "/lastFive")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> students = studentService.getLastFiveStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Collection<Student>> findStudentsByName(@PathVariable("name") String name) {
        Collection<Student> students = studentService.findStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/get/startA")
    public ResponseEntity<Collection<String>> findAllStudentsStartWithA() {
        return ResponseEntity.ok(studentService.findAllStudentsStartWithA());
    }

    @GetMapping("/getAverageAge")
    public Double getStudentAverageAge() {
        return studentService.getStudentAverageAge();
    }
}

