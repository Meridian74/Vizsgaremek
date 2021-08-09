package com.codecool.vizsgaremek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShiftCommand {

   private String name;
   private LocalTime startTime;
   private int durations;
   private int restTime;

}
