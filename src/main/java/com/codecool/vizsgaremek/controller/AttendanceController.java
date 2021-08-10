package com.codecool.vizsgaremek.controller;

import com.codecool.vizsgaremek.dto.CreateDateCommand;
import com.codecool.vizsgaremek.dto.EmployeeDTO;
import com.codecool.vizsgaremek.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

   private AttendanceService attendanceService;

   public AttendanceController(AttendanceService attendanceService) {
      this.attendanceService = attendanceService;
   }

   @PostMapping("/addshift")
   @ResponseStatus(HttpStatus.CREATED)
   public EmployeeDTO addShiftToEmployee(
         @RequestParam("shiftid") long shiftId,
         @RequestParam("employeeid") long employeeId,
         @RequestBody CreateDateCommand command) {

      return attendanceService.addShiftToEmployee(shiftId, employeeId, command);
   }

}
