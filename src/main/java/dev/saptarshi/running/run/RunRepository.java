package dev.saptarshi.running.run;

import java.util.List;
import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

// @Repository
// public class RunRepository {
//     private List<Run> runs = new ArrayList<>(); // Acting as in memory db

//     List<Run> findAll() {
//         return runs;
//     }

//     Optional<Run> findById(Integer id) {
//         return runs.stream()
//                 .filter(run -> run.id.equals(id)) // for obj comparision use .equal
//                 .findFirst();
//     }

//     void create(Run run) {
//         runs.add(run);
//     }

//     void update(Run run, Integer id) {
//         Optional<Run> existingRun = findById(id);
//         if(existingRun.isPresent()) {
//             runs.set(runs.indexOf(existingRun.get()), run);
//         }
//     }

//     void delete(Integer id) {
//         runs.removeIf(run -> run.id.equals(id));
//     }

//     @PostConstruct
//     public void init() {
//         runs.add(new Run(1, 
//         "Monday Run",
//         LocalDateTime.now(), 
//         LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 
//         3, 
//         Location.INDOOR));

//         runs.add(new Run(2, 
//         "Tuesday run", 
//         LocalDateTime.now(), 
//         LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
//         6,
//         Location.OUTDOOR));
//     }

// }

@Repository
public class RunRepository {

    // private static final Logger log =
    // LoggerFactory.getLogger(RunRepository.class);

    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Run> findAll() {
        return jdbcClient.sql("select * from Run").query(Run.class).list();
    }

    Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }
    
    public void create(Run run) {
        var created = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?)")
            .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
            .update();
        Assert.state(created == 1, "Failed to create " + run.title());
    }
    
    void update(Run run, Integer id) {
        var updated = jdbcClient.sql("update Run set title=?, started_on=?, completed_on=?,miles=?,location=? where id=?")
            .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
            .update();
        Assert.state(updated == 1, "Failed to update " + run.title());
    }
    
    void delete(Integer id) {
        var deleted = jdbcClient.sql("delete from Run where id = :id")
            .param("id", id)
            .update();
        Assert.state(deleted == 1, "Failed to delete run id " + id);
    }
    
    int count() {
        return jdbcClient.sql("select * from Run").query().listOfRows().size();
    }
    
    void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }
    
    List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from Run where location = :location")
            .param("location", location)
            .query(Run.class)
            .list();
    }
}