package com.codecool.vizsgaremek;


import com.codecool.vizsgaremek.model.Employee;
import com.codecool.vizsgaremek.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class EmployeeRepositoryIT {

   @Autowired
   EmployeeRepository employeeRepository;

   @Test
   void testPersist() {
      Employee employee = new Employee();
      employee.setName("Teszt István");
      employee.setBirthDate(LocalDate.parse("2003-04-20"));
      employeeRepository.save(employee);

      List<Employee> employees = employeeRepository.findAll();

      assertThat(employees)
            .extracting(Employee::getName)
            .containsExactly("Teszt István");

   }

}
