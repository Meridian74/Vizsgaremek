package com.codecool.vizsgaremek.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeCommand {

   @Schema(description = "Name of the new employee", example = "Teszt Jakab")
   private String name;
   @Schema(description = "Birth date of the new employee", example = "1998-03-19")
   private LocalDate birthDate;

}
