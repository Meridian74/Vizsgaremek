package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.dto.CreateEmployeeCommand;
import com.codecool.vizsgaremek.dto.EmployeeDTO;
import com.codecool.vizsgaremek.dto.UpdateEmployeeCommand;
import com.codecool.vizsgaremek.exception.EmployeeNotFoundException;
import com.codecool.vizsgaremek.model.Employee;
import com.codecool.vizsgaremek.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

   private ModelMapper modelMapper;
   private EmployeeRepository employeeRepository;

   public EmployeeService(ModelMapper modelMapper, EmployeeRepository employeeRepository) {
      this.modelMapper = modelMapper;
      this.employeeRepository = employeeRepository;
   }


   public EmployeeDTO createEmployee(CreateEmployeeCommand command) {
      Employee employee = new Employee();
      employee.setName(command.getName());
      employee.setBirthDate(command.getBirthDate());

      employee = employeeRepository.save(employee);
      return modelMapper.map(employee, EmployeeDTO.class);

   }

   public EmployeeDTO findEmployeeById(long id) {
      Optional<Employee> optionalEmployee = employeeRepository.findById(id);

      if (optionalEmployee.isPresent()) {
         return modelMapper.map(optionalEmployee.get(), EmployeeDTO.class);
      }
      else {
         throw new EmployeeNotFoundException(id);
      }
   }

   public EmployeeDTO updateEmployee(long id, UpdateEmployeeCommand command) {
      Optional<Employee> optionalEmployee = employeeRepository.findById(id);

      if (optionalEmployee.isPresent()) {
         Employee employee = optionalEmployee.get();
         if (command.getName() != null) {
            employee.setName(command.getName());
         }
         if (command.getBirthDate() != null) {
            employee.setBirthDate(command.getBirthDate());
         }
         employeeRepository.save(employee);
         return modelMapper.map(employee, EmployeeDTO.class);
      }
      else {
         throw new EmployeeNotFoundException(id);
      }

   }

   public void deleteEmployeeById(long id) {
      Optional<Employee> optionalEmployee = employeeRepository.findById(id);
      if (optionalEmployee.isPresent()) {
         employeeRepository.deleteById(id);
      }
      else {
         throw new EmployeeNotFoundException(id);
      }
   }

   public List<EmployeeDTO> employeeList(Optional<String> prefix) {
      Type targetListType = new TypeToken<List<EmployeeDTO>>(){}.getType();

      List<Employee> employees = employeeRepository.findAll();
      List<Employee> filtered = employees.stream()
            .filter(e -> prefix.isEmpty()
                  || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
            .collect(Collectors.toList());

      return modelMapper.map(filtered, targetListType);
   }


}
