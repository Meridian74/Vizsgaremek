package com.codecool.vizsgaremek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Entity
@Table(name = "SHIFTS")
public class Shift {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "SHIFT_ID")
   private long id;

   @Column(name = "SHIFT_NAME")
   private String shiftName;

   @Column(name = "EXPECTED_START_TIME")
   @JsonFormat(pattern="HH:mm:ss")
   private LocalTime expectedStartTime;

   @Column(name = "DURATION_IN_HOURS")
   private int durationInHours;

   @Column(name = "REST_TIME_IN_MINUTES")
   private int restTimeInMinutes;

}
