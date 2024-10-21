package dev.saptarshi.running.run;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.saptarshi.running.Application;

@Component
public class RunJsonLoader implements CommandLineRunner {
    
	private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    private final RunRepository runRepository;

    private final ObjectMapper objectMapper;

    
    public RunJsonLoader(RunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                if (inputStream == null) {
                    log.error("Failed to load runs.json");
                    return;
                }
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to database", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
                log.info("Reading {} runs from JSON data and saving to database", allRuns.runs().size());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON because the collection contains data.");
        }
    }
        
}
