package dev.saptarshi.running.run;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

 public record Run (
     Integer id,
     @NotBlank
     String title,
     LocalDateTime startedOn,
     LocalDateTime completedOn,
     @Positive
     Integer miles,
     Location location
 ) {
     public Run {
         if(!completedOn.isAfter(startedOn)) {
             throw new IllegalArgumentException("Completed On must be after Started On");
         }
     }
 }
