package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.dto.CreateDateCommand;
import com.codecool.vizsgaremek.dto.EmployeeDTO;
import com.codecool.vizsgaremek.exception.EmployeeNotFoundException;
import com.codecool.vizsgaremek.model.Attendance;
import com.codecool.vizsgaremek.model.Employee;
import com.codecool.vizsgaremek.model.Shift;
import com.codecool.vizsgaremek.repository.EmployeeRepository;
import com.codecool.vizsgaremek.repository.ShiftRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

   public EmployeeDTO addShiftToEmployee(long shiftId, long employeeId, CreateDateCommand command) {

      // select employee
      Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
      if (optionalEmployee.isEmpty()) {
         throw new EmployeeNotFoundException(employeeId);
      }
      Employee employee = optionalEmployee.get();

      // select planned shift
      Optional<Shift> optionalShift = shiftRepository.findById(shiftId);
      if (optionalEmployee.isEmpty()) {
         throw new EmployeeNotFoundException(employeeId);
      }
      Shift shift = optionalShift.get();

      // setting shift for employee at shiftDate
      Attendance attendance = new Attendance();
      attendance.setCurrentDailyShift(shift);

      Map dailyAttendancesOfEmployee = employee.getDailyAttendances();
      LocalDate shiftDate = command.getDate();
      dailyAttendancesOfEmployee.put(shiftDate, attendance);

      return modelMapper.map(optionalEmployee.get(), EmployeeDTO.class);
   }
}
