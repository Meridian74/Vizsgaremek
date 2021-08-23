package com.codecool.vizsgaremek;


import com.codecool.vizsgaremek.dto.CreateEmployeeCommand;
import com.codecool.vizsgaremek.dto.EmployeeDTO;
import com.codecool.vizsgaremek.dto.UpdateEmployeeCommand;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class EmployeeControllerIT {

   @LocalServerPort
   private int port;

   private String baseUrl;

   @Autowired
   private TestRestTemplate testRestTemplate;

   @BeforeEach
   public void setUp() {
      this.baseUrl = "http://localhost:" + port + "/api/employees";
   }



   @Test
   public void getEmployees_emptyDatabase_returnsEmptyList() {
      List<EmployeeDTO> employees = List.of(testRestTemplate.getForObject(baseUrl, EmployeeDTO[].class));
      assertEquals(0, employees.size());
   }

   @Test
   public void addNewEmployeeToDatabase_shouldReturnSameEmployee() {
      CreateEmployeeCommand command = new CreateEmployeeCommand();
      command.setName("Teszt István");
      command.setBirthDate(LocalDate.parse("2000-01-10"));

      EmployeeDTO result = testRestTemplate.postForObject(baseUrl, command, EmployeeDTO.class);
      assertEquals(command.getName(), result.getName());
   }

   @Test
   public void getEmployeeById_withOnePostedEmployee_returnsEmployeeWithSameId() {
      CreateEmployeeCommand command = new CreateEmployeeCommand("Teszt Ubul", LocalDate.parse("2000-01-01"));
      EmployeeDTO returnedDTO = testRestTemplate.postForObject(baseUrl, command, EmployeeDTO.class);
      EmployeeDTO resultDTO = testRestTemplate.getForObject(baseUrl + "/" + returnedDTO.getId(), EmployeeDTO.class);
      assertEquals(returnedDTO.getId(), resultDTO.getId());
   }

   @Test
   public void updateEmployeeName_withOnePostedEmployee_returnsUpdatedEmployee() {
      CreateEmployeeCommand command = new CreateEmployeeCommand("Teszt Joe", LocalDate.parse("2000-01-01"));
      EmployeeDTO returnedDTO = testRestTemplate.postForObject(baseUrl, command, EmployeeDTO.class);

      UpdateEmployeeCommand updateCommand = new UpdateEmployeeCommand();
      updateCommand.setName("Updated Joe");
      long id = returnedDTO.getId();

      testRestTemplate.put(baseUrl + "/" + id, updateCommand);
      returnedDTO = testRestTemplate.getForObject(baseUrl + "/" + id, EmployeeDTO.class);

      assertEquals("Updated Joe", returnedDTO.getName());

   }

   @Test
   public void deleteEmployeeById_withSomePostedEmployee_getAllShouldReturnRemainingEmployees() {
      CreateEmployeeCommand command1 = new CreateEmployeeCommand("User1 teszt", LocalDate.parse("2000-01-01"));
      CreateEmployeeCommand command2 = new CreateEmployeeCommand("User2 teszt", LocalDate.parse("2001-02-02"));
      CreateEmployeeCommand command3 = new CreateEmployeeCommand("User3 teszt", LocalDate.parse("2002-03-03"));
      testRestTemplate.postForObject(baseUrl, command1, EmployeeDTO.class);
      testRestTemplate.postForObject(baseUrl, command2, EmployeeDTO.class);
      testRestTemplate.postForObject(baseUrl, command3, EmployeeDTO.class);

      List<EmployeeDTO> employees = List.of(testRestTemplate.getForObject(baseUrl + "?prefix=User2", EmployeeDTO[].class));
      long id = employees.get(0).getId();

      testRestTemplate.delete(baseUrl + "/" + id);

      List<EmployeeDTO> remainingEmployees = List.of(testRestTemplate.getForObject(baseUrl, EmployeeDTO[].class));

      assertEquals(2, remainingEmployees.size());

   }


}
