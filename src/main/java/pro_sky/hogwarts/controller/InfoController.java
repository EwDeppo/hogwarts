package pro_sky.hogwarts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro_sky.hogwarts.service.StudentService;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @GetMapping("/number")
    public void getIntegerValue() {
        long startOne = System.currentTimeMillis();
        int resultOne = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finishOne = System.currentTimeMillis() - startOne;
        logger.info("Время выполнения " + finishOne);

        long startTwo = System.currentTimeMillis();
        int resultTwo = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long finishTwo = System.currentTimeMillis() - startTwo;
        logger.info("Время выполнения " + finishTwo);
    }
    /*/// ////////
    @GetMapping
    public Integer getNumber() {
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }

    /// /////////
    @GetMapping
    public Integer getNumber2() {
        return IntStream.range(1, 1_000_000)
                .reduce(0, Integer::sum);
    }*/
}
