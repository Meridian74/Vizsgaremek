package com.codecool.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Name and birth date for the new employee", example = "Teszt Jakab, 1998-03-01")
public class CreateEmployeeCommand {

   private String name;
   private LocalDate birthDate;

}
