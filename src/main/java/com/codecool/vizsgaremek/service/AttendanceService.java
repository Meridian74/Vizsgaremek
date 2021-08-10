package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.dto.*;
import com.codecool.vizsgaremek.exception.EmployeeNotFoundException;
import com.codecool.vizsgaremek.exception.DateNotFoundException;
import com.codecool.vizsgaremek.exception.ShiftNotFoundException;
import com.codecool.vizsgaremek.model.Attendance;
import com.codecool.vizsgaremek.model.Employee;
import com.codecool.vizsgaremek.model.Shift;
import com.codecool.vizsgaremek.repository.EmployeeRepository;
import com.codecool.vizsgaremek.repository.ShiftRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {

   private ModelMapper modelMapper;
   private EmployeeRepository employeeRepository;
   private ShiftRepository shiftRepository;

   public AttendanceService(ModelMapper modelMapper, EmployeeRepository employeeRepository, ShiftRepository shiftRepository) {
      this.modelMapper = modelMapper;
      this.employeeRepository = employeeRepository;
      this.shiftRepository = shiftRepository;
   }

   @Transactional
   public AttendanceOfEmployeeDTO addShiftToEmployee(long shiftId, long employeeId, CreateDateCommand command) {
      Employee employee = selectEmployee(employeeId);
      Shift shift = selectPlannedShift(shiftId);
      LocalDate shiftDate = command.getDate();

      Attendance attendance = new Attendance();
      attendance.setCurrentDailyShift(shift);

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();
      dailyAttendancesOfEmployee.put(shiftDate, attendance);
      employee.setDailyAttendances(dailyAttendancesOfEmployee);

      return createAttendanceOfEmployeeDto(employee, shiftDate, attendance);
   }

   @Transactional
   public AttendanceOfEmployeeDTO replaceShift(long employeeId, long newShiftId, CreateDateCommand command) {
      Employee employee = selectEmployee(employeeId);
      Shift newShift = selectPlannedShift(newShiftId);
      LocalDate shiftDate = command.getDate();

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();

      if (dailyAttendancesOfEmployee.get(shiftDate) == null ||
            !(dailyAttendancesOfEmployee.get(shiftDate) instanceof Attendance)) {
         throw new DateNotFoundException(shiftDate);
      }
      Attendance attendance = (Attendance) dailyAttendancesOfEmployee.get(shiftDate);

      attendance.setCurrentDailyShift(newShift);
      dailyAttendancesOfEmployee.put(shiftDate, attendance);
      employee.setDailyAttendances(dailyAttendancesOfEmployee);

      return createAttendanceOfEmployeeDto(employee, shiftDate, attendance);
   }

   public AttendanceOfEmployeeDTO getEmployeeAttendanceByDate(long employeeId, CreateDateCommand command) {
      Employee employee = selectEmployee(employeeId);
      LocalDate shiftDate = command.getDate();

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();
      if (dailyAttendancesOfEmployee.get(shiftDate) == null ||
            !(dailyAttendancesOfEmployee.get(shiftDate) instanceof Attendance)) {
         throw new DateNotFoundException(shiftDate);
      }
      Attendance attendance = (Attendance) dailyAttendancesOfEmployee.get(shiftDate);

      return createAttendanceOfEmployeeDto(employee, shiftDate, attendance);
   }

   public EmployeeWithAttendencesDTO getListOfAttendancesOfEmployee(long employeeId) {
      Employee employee = selectEmployee(employeeId);
      return modelMapper.map(employee, EmployeeWithAttendencesDTO.class);
   }

   @Transactional
   public void setEntryTime(CreateEntryCommand command) {
      Employee employee = selectEmployee(command.getEmployeeId());
      LocalDate shiftDate = command.getShiftDate();

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();
      if (dailyAttendancesOfEmployee.get(shiftDate) == null) {
         throw new DateNotFoundException(shiftDate);
      }

      Attendance attendance = (Attendance) dailyAttendancesOfEmployee.get(shiftDate);
      attendance.setEntryTime(command.getTimestamp());

      dailyAttendancesOfEmployee.replace(shiftDate, attendance);
      employee.setDailyAttendances(dailyAttendancesOfEmployee);

   }

   @Transactional
   public void setExitTime(CreateExitCommand command) {
      Employee employee = selectEmployee(command.getEmployeeId());
      LocalDate shiftDate = command.getShiftDate();

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();
      if (dailyAttendancesOfEmployee.get(shiftDate) == null) {
         throw new DateNotFoundException(shiftDate);
      }

      Attendance attendance = (Attendance) dailyAttendancesOfEmployee.get(shiftDate);
      attendance.setExitTime(command.getTimestamp());

      dailyAttendancesOfEmployee.replace(shiftDate, attendance);
      employee.setDailyAttendances(dailyAttendancesOfEmployee);

   }




   private Employee selectEmployee(long employeeId) {
      Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
      if (optionalEmployee.isEmpty()) {
         throw new EmployeeNotFoundException(employeeId);
      }
      return optionalEmployee.get();
   }

   private Shift selectPlannedShift(long shiftId) {
      Optional<Shift> optionalShift = shiftRepository.findById(shiftId);
      if (optionalShift.isEmpty()) {
         throw new ShiftNotFoundException(shiftId);
      }
      return optionalShift.get();
   }

   private AttendanceOfEmployeeDTO createAttendanceOfEmployeeDto(Employee employee, LocalDate shiftDate, Attendance attendance) {
      AttendanceOfEmployeeDTO result = new AttendanceOfEmployeeDTO();

      result.setId(employee.getId());
      result.setName(employee.getName());
      result.setAttendeanceOfOneDate(attendance);
      result.setDateOfAttendance(shiftDate);

      return result;
   }

}



