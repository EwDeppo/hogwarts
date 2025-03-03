package pro_sky.hogwarts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.dto.StudentDto;
import pro_sky.hogwarts.repository.StudentRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private final StudentRepository studentRepository;

    public StudentDto createStudent(Student student) {
        log.info("Was invoked method for create student - {}", student);
        var savedStudent = studentRepository.save(student);
        return convertDto(savedStudent);
    }

    private StudentDto convertDto(Student student) {
        var studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAge(student.getAge());
        return studentDto;
    }

    public Student editStudent(Long id, Student student) {
        var studentForUpdate = studentRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Student this ID " + id + " not found";
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
        studentForUpdate.setName(student.getName());
        studentForUpdate.setAge(student.getAge());
        log.info("Was invoked method for edit student - {}", student);
        return studentRepository.save(studentForUpdate);
    }

    public void deleteStudent(long id) {
        log.info("Was invoked method for delete student by id - {}", id);
        studentRepository.deleteById(id);
    }

    public Student findStudentById(long id) {
        log.info("Was invoked method for find student by {}", id);
        return studentRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Student this ID " + id + " not found";
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    public List<Student> findAllStudents() {
        log.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByAge(@NonNull Long age) {
        log.info("Was invoked method for find students by age {}", age);
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> findByAgeBetween(@NonNull Long min, @NonNull Long max) {
        log.info("Was invoked method for find students by age between {} and {}", min, max);
        if (min > max) {
            log.error("Min age {} is greater than max age {}", min, max);
            throw new IllegalArgumentException("Min age cannot be greater than max age");
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<String> findAllStudentsStartWithA() {
        log.info("Was invoked method for find students start name with 'A'");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted(String::compareTo)
                .toList();
    }

    public Double getStudentAverageAge() {
        log.info("Was invoked method find student average age");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(EntityNotFoundException::new);
    }

    public int getStudents() {
        log.info("The method was called to display the number of students");
        return studentRepository.getStudents();
    }

    public int getAverageAge() {
        log.info("The method was called to display the average age of students");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        log.info("A method was called to display the last 5 students");
        return studentRepository.getLastFiveStudents();
    }

    public List<Student> findStudentsByName(String name) {
        log.info("Was invoked method for find students by name - {}", name);
        return studentRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public void printNamesStudents() {
        List<Student> students = studentRepository.findAll();
        threadPoolTaskExecutor.execute(() -> {
            System.out.println(students.get(0).getName());
            System.out.println(students.get(3).getName());
        });

        threadPoolTaskExecutor.execute(() -> {
            System.out.println(students.get(1).getName());
            System.out.println(students.get(2).getName());
        });
    }
}
