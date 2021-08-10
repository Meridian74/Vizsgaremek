package com.codecool.vizsgaremek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEntryCommand {

   private long employeeId;
   private LocalDateTime timestamp;
   private LocalDate shiftDate;

}
