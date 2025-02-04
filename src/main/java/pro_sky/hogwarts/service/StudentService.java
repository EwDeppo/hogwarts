package pro_sky.hogwarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(Long age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(Long min, Long max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public int getStudents() {
        return studentRepository.getStudents();
    }

    public int getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public List<Student> findStudentsByName(String name) {
        return studentRepository.findStudentsByName(name);
    }
}
