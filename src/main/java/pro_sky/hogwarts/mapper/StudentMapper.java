package pro_sky.hogwarts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pro_sky.hogwarts.dto.StudentDto;
import pro_sky.hogwarts.entity.Student;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    StudentDto toStudentDto(Student student);

    Student fromStudentDto(StudentDto studentDto);

    List<StudentDto> toStudentsDtoList(List<Student> students);
}
