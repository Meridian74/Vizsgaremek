package com.codecool.vizsgaremek.dto;

import com.codecool.vizsgaremek.model.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithAttendencesDTO {

   private long id;
   private String name;

   Map<LocalDate, Attendance> dailyAttendances = new HashMap<>();

}
