package com.codecool.vizsgaremek.controller;

import com.codecool.vizsgaremek.dto.CreateEmployeeCommand;
import com.codecool.vizsgaremek.dto.EmployeeDTO;
import com.codecool.vizsgaremek.dto.UpdateEmployeeCommand;
import com.codecool.vizsgaremek.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Tag(name = " Operations of Employees")
public class EmployeeController {

   private EmployeeService employeeService;

   public EmployeeController(EmployeeService es) {
      this.employeeService = es;
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   @Operation(summary = "Creates an employee")
   @ApiResponse(responseCode = "201", description = "Employee has been created")
   public EmployeeDTO createEmployee(@RequestBody CreateEmployeeCommand command) {
      return employeeService.createEmployee(command);
   }

   @GetMapping("/{id}")
   @Operation(summary = "find and get an employee by its id")
   public EmployeeDTO findEmployeeById(@PathVariable("id") long id) {
      return employeeService.findEmployeeById(id);
   }

   @PutMapping("/{id}")
   @Operation(summary = "Updating and an employee by its id and new datas")
   public EmployeeDTO updateEmployee(
         @PathVariable("id") long id,
         @RequestBody UpdateEmployeeCommand command) {
      return employeeService.updateEmployee(id, command);
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Operation(summary = "Delete an employee by given its id")
   @ApiResponse(responseCode = "204", description = "Employee has been deleted")
   public void deleteEmployee(@PathVariable("id") long id) {
      employeeService.deleteEmployeeById(id);
   }

   @GetMapping
   @Operation(summary = "List all employee filtered with optional prefix string")
   public List<EmployeeDTO> employeeList(@RequestParam Optional<String> prefix) {
      return employeeService.employeeList(prefix);
   }


}
