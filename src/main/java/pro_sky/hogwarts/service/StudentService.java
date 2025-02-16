package pro_sky.hogwarts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student - {}", student);
        return studentRepository.save(student);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student - {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student {}", id);
        studentRepository.deleteById(id);
    }

    public Student findStudentById(long id) {
        logger.info("Was invoked method for find student by {}", id);
        return studentRepository.findById(id).get();
    }

    public Collection<Student> findAllStudent() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsByAge(Long age) {
        logger.info("Was invoked method for find students by age {}", age);
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeBetween(Long min, Long max) {
        logger.info("Was invoked method for find students by age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    /// //////////////////////////////////////////////////
    public List<String> findAllStudents2() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted(String::compareTo)
                .toList();
    }

    public int getStudents() {
        logger.info("The method was called to display the number of students");
        return studentRepository.getStudents();
    }

    public int getAverageAge() {
        logger.info("The method was called to display the average age of students");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("A method was called to display the last 5 students");
        return studentRepository.getLastFiveStudents();
    }

    public Collection<Student> findStudentsByName(String name) {
        logger.info("Was invoked method for find students by name - {}", name);
        return studentRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
