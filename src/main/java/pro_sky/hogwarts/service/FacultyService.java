package pro_sky.hogwarts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro_sky.hogwarts.dto.FacultyDto;
import pro_sky.hogwarts.entity.Faculty;
import pro_sky.hogwarts.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacultyService {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyDto createFaculty(Faculty faculty) {
        var sql = """
                INSERT INTO faculty (name, color)
                VALUES (?, ?)
                """;
        jdbcTemplate.update(sql, faculty.getName(), faculty.getColor());
        return convertDto(faculty);
    }

    public FacultyDto editFaculty(Long id, Faculty faculty) {
        var sql = """
                UPDATE Faculty
                SET name=?, color=?
                WHERE id=?
                """;
        jdbcTemplate.update(sql, faculty.getName(), faculty.getColor(), id);
        return convertDto(faculty);
    }

    public void deleteFaculty(Long id) {
        var sql = """
                DELETE FROM Faculty
                WHERE id=?
                """;
        jdbcTemplate.update(sql, id);
    }

    public List<FacultyDto> findAllFaculties() {
        var sql = """
                SELECT * 
                FROM Faculty
                """;
        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Faculty.class));
        var faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(this::convertDto)
                .toList();
    }

    public FacultyDto findFacultyById(Long id) {
        var sql = """
                SELECT *
                FROM Faculty
                WHERE id=?
                """;
        var faculty = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class))
                .stream().findAny().orElse(null);
        return convertDto(faculty);
    }

    private FacultyDto convertDto(Faculty faculty) {
        var facultyDto = new FacultyDto();
        facultyDto.setId(faculty.getId());
        facultyDto.setName(faculty.getName());
        facultyDto.setColor(faculty.getColor());
        return facultyDto;
    }

    public List<FacultyDto> findByNameOrColorContainingIgnoreCase(String query) {
        log.info("Was invoked method for find faculty by name or color");
        return facultyRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()) || e.getColor().toLowerCase().contains(query.toLowerCase()))
                .map(this::convertDto)
                .toList();
    }

    public Optional<String> getLongName() {
        log.info("Was invoked method for find faculty with the long name");
        return facultyRepository.findAll().stream()
                .map(this::convertDto)
                .map(FacultyDto::getName)
                .max(Comparator.comparingInt(String::length));
    }
}
