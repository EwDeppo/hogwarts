package pro_sky.hogwarts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import pro_sky.hogwarts.entity.Faculty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllFaculties() {
        ResponseEntity<Faculty> newFacultyResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", new Faculty(1L, "Name1", "Red"), Faculty.class);
        ResponseEntity<Faculty[]> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", Faculty[].class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).isNull();

        Faculty[] faculties = response.getBody();
        assertThat(faculties[0].getName()).isEqualTo("Name1");
    }
}
