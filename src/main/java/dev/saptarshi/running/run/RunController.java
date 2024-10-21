package dev.saptarshi.running.run;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;



@RestController()
@RequestMapping("/api/run")
public class RunController {
    
    private final RunRepository runRepository;
    
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    public List<Run> findAll() {
        return runRepository.findAll();
    }
    
    @GetMapping("/find/{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    void createRun(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateRun(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run, id);
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRun(@PathVariable Integer id) {
        runRepository.delete(id);
    }
    
    @GetMapping("/getCount")
    int countRun() {
        return runRepository.count();
    }

    @GetMapping("/location/{location}") 
    List<Run> findByLocation(@PathVariable String location) {
        return runRepository.findByLocation(location);
    }
    
}
