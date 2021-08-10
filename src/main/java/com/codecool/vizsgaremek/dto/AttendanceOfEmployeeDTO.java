package com.codecool.vizsgaremek.dto;

import com.codecool.vizsgaremek.model.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceOfEmployeeDTO {

   private long id;
   private String name;

   private Attendance AttendeanceOfOneDate;
   private LocalDate dateOfAttendance;

}
