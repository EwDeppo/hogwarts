package pro_sky.hogwarts.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        log.info("Was invoked method for create student - {}", student);
        return studentRepository.save(student);
    }

    public Student editStudent(long id, Student student) {
        var studentForUpdate = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        log.info("Was invoked method for edit student - {}", student);
        studentForUpdate.setName(student.getName());
        studentForUpdate.setAge(student.getAge());
        return studentRepository.save(studentForUpdate);
    }

    public void deleteStudent(long id) {
        log.info("Was invoked method for delete student {}", id);
        studentRepository.deleteById(id);
    }

    public Student findStudentById(long id) {
        log.info("Was invoked method for find student by {}", id);
        return studentRepository.findById(id).get();
    }

    public Collection<Student> findAllStudents() {
        log.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsByAge(Long age) {
        log.info("Was invoked method for find students by age {}", age);
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeBetween(Long min, Long max) {
        log.info("Was invoked method for find students by age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<String> findAllStudentsStartWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted(String::compareTo)
                .toList();
    }

    public Double getStudentAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow();
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

    public Collection<Student> findStudentsByName(String name) {
        log.info("Was invoked method for find students by name - {}", name);
        return studentRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public void printNamesStudents() {
        List<Student> students = studentRepository.findAll();
        new Thread(() -> {
            System.out.println(students.get(0).getName());
            System.out.println(students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(1).getName());
            System.out.println(students.get(2).getName());
        }).start();
    }

    public void printNamesStudentsSync() {
        List<Student> students = studentRepository.findAll();

        new Thread(() -> {
            printName(students.get(0));
            printName(students.get(3));
        }).start();

        new Thread(() -> {
            printName(students.get(1));
            printName(students.get(2));
        }).start();
    }

    public synchronized void printName(Student student) {
        System.out.println(student.getName());
    }
}
