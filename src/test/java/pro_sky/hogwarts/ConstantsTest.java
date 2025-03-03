package pro_sky.hogwarts;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.entity.Student;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class ConstantsTest {
    public Long ID_STUDENT = 1L;
    public Long ID_FACULTY = 1L;
    public String NAME_STUDENT = "Aaaa";
    public String NAME_FACULTY = "First Faculty";
    public String NAME_FACULTY_TWO = "Second Faculty";
    public int AGE = 15;
    public int AGE_MIN = 10;
    public int AGE_MAX = 20;
    public String COLOR = "Red";
    public String COLOR_TWO = "Blue";
    public Student STUDENT = new Student();
    public Faculty FACULTY = new Faculty();
    public Faculty FACULTY_TWO = new Faculty();
    public JSONObject FACULTY_OBJECT = new JSONObject();
    public JSONObject STUDENT_OBJECT = new JSONObject();
    public Collection<Faculty> FACULTIES = List.of(FACULTY);
    public Collection<Student> STUDENTS = List.of(STUDENT);
    public LinkedMultiValueMap<String, Object> BODY = new LinkedMultiValueMap<>();
}
