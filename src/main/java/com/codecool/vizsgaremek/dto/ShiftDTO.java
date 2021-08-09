package com.codecool.vizsgaremek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDTO {

   private long id;
   private String name;
   private LocalTime expectedStartTime;
   private int durationInHours;
   private int restTimeInMinutes;

}
