package pro_sky.hogwarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.entity.Student;
import pro_sky.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findByAge(int age) {
        return getAllStudents().stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }
}
