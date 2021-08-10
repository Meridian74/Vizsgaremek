package com.codecool.vizsgaremek.controller;

import com.codecool.vizsgaremek.dto.EmployeeWithAttendencesDTO;
import com.codecool.vizsgaremek.dto.AttendanceOfEmployeeDTO;
import com.codecool.vizsgaremek.dto.CreateDateCommand;
import com.codecool.vizsgaremek.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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

   @GetMapping("/list-of-attendances/{id}")
   public EmployeeWithAttendencesDTO getListOfAttendancesOfEmployee(
         @PathVariable("id") long employeeId) {
      return attendanceService.getListOfAttendancesOfEmployee(employeeId);
   }

}
