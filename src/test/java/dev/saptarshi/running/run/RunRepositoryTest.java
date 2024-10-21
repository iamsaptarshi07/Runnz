package dev.saptarshi.running.run;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(RunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RunRepositoryTest {

    @Autowired
    RunRepository runRepository;

    @BeforeEach
    void setup() {
         runRepository.create(new Run(1,
                 "Monday Morning Run",
                 LocalDateTime.now(),
                 LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                 6,
                 Location.INDOOR));

        runRepository.create(new Run(2,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                6,
                Location.INDOOR));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = runRepository.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = runRepository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(6, run.miles());
    }

    @Test
    void shouldNotFindRunWithInvalidId() {
        var run = runRepository.findById(3);
        assertTrue(run.isEmpty());
    }

    @Test
    void shouldCreateNewRUn() {
        runRepository.create(new Run(3,
                "Tuesday",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR));
        List<Run> runs = runRepository.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() {
        runRepository.update(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                6,
                Location.INDOOR), 1);
        var run = runRepository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(6, run.miles());
        assertEquals(Location.INDOOR, run.location());
    }

    @Test
    void shouldDelete() {
        runRepository.delete(1);
        List<Run> runs = runRepository.findAll();
        assertEquals(1, runs.size());
    }

}