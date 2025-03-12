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
    public static final Long ID_STUDENT = 1L;
    public static final Long ID_FACULTY = 1L;
    public static final String NAME_STUDENT = "Aaaa";
    public static final String NAME_FACULTY = "First Faculty";
    public static final String NAME_FACULTY_TWO = "Second Faculty";
    public static final int AGE = 15;
    public static final int AGE_MIN = 10;
    public static final int AGE_MAX = 20;
    public static final String COLOR = "Red";
    public static final String COLOR_TWO = "Blue";
    public static final Student STUDENT = new Student();
    public static final Faculty FACULTY = new Faculty();
    public static final Faculty FACULTY_TWO = new Faculty();
    public static final JSONObject FACULTY_OBJECT = new JSONObject();
    public static final JSONObject STUDENT_OBJECT = new JSONObject();
    public static final Collection<Faculty> FACULTIES = List.of(FACULTY);
    public static final Collection<Student> STUDENTS = List.of(STUDENT);
    public static final LinkedMultiValueMap<String, Object> BODY = new LinkedMultiValueMap<>();
}
