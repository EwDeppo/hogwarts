package pro_sky.hogwarts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro_sky.hogwarts.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void contextLoads() {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void testGetStudent() throws Exception {
		Assertions
				.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
				.isNotEmpty();
	}

}
