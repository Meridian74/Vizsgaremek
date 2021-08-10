package com.codecool.vizsgaremek.controller;

import com.codecool.vizsgaremek.dto.*;
import com.codecool.vizsgaremek.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

   private AttendanceService attendanceService;

   public AttendanceController(AttendanceService attendanceService) {
      this.attendanceService = attendanceService;
   }

   @PostMapping("/add-shift")
   @ResponseStatus(HttpStatus.CREATED)
   public AttendanceOfEmployeeDTO addShiftToEmployee(
         @RequestParam("emp_id") long employeeId,
         @RequestParam("shift_id") long shiftId,
         @RequestBody CreateDateCommand command) {

      return attendanceService.addShiftToEmployee(employeeId, shiftId, command);
   }

   @PostMapping("/replace-shift")
   @ResponseStatus(HttpStatus.CREATED)
   public AttendanceOfEmployeeDTO replaceShift(
         @RequestParam("emp_id") long employeeId,
         @RequestParam("new_shift_id") long newShiftId,
         @RequestBody CreateDateCommand command) {

      return attendanceService.replaceShift(employeeId, newShiftId, command);
   }
   @GetMapping("/shift-at-date")
   public AttendanceOfEmployeeDTO getEmployeeAttendanceByDate(
         @RequestParam("emp_id") long employeeId,
         @RequestBody CreateDateCommand command) {
      return attendanceService.getEmployeeAttendanceByDate(employeeId, command);
   }

   @GetMapping("/list-of-attendances")
   public EmployeeWithAttendencesDTO getListOfAttendancesOfEmployee(
         @RequestParam("emp_id") long employeeId) {
      return attendanceService.getListOfAttendancesOfEmployee(employeeId);
   }

   @PutMapping("entry")
   public void setEntryTime(@RequestBody CreateEntryCommand command) {
      attendanceService.setEntryTime(command);
   }

   @PutMapping("exit")
   public void setEntryTime(@RequestBody CreateExitCommand command) {
      attendanceService.setExitTime(command);
   }


}
