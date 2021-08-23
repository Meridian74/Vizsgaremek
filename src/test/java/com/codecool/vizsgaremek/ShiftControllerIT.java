package com.codecool.vizsgaremek;

import java.time.LocalTime;
import java.util.List;

import com.codecool.vizsgaremek.dto.*;
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
public class ShiftControllerIT {

   @LocalServerPort
   private int port;

   private String baseUrl;

   @Autowired
   private TestRestTemplate testRestTemplate;

   @BeforeEach
   public void setUp() {
      this.baseUrl = "http://localhost:" + port + "/api/shift";
   }


   @Test
   public void getShift_emptyDatabase_returnsEmptyList() {
      List<ShiftDTO> shifts = List.of(testRestTemplate.getForObject(baseUrl, ShiftDTO[].class));
      assertEquals(0, shifts.size());
   }

   @Test
   public void addNewShiftToDatabase_shouldReturnSameShift() {
      CreateShiftCommand command = new CreateShiftCommand();
      command.setName("Alap műszak");
      command.setStartTime(LocalTime.parse("08:00:00"));
      command.setDuration(8);
      command.setRestTime(60);

      ShiftDTO result = testRestTemplate.postForObject(baseUrl, command, ShiftDTO.class);
      assertEquals(command.getName(), result.getName());
   }

   @Test
   public void getShiftById_withOnePostedShift_returnsShiftWithSameId() {
      CreateShiftCommand command = new CreateShiftCommand();
      command.setName("Esti műszak");
      command.setStartTime(LocalTime.parse("19:00:00"));
      command.setDuration(8);
      command.setRestTime(60);

      ShiftDTO returnedDTO = testRestTemplate.postForObject(baseUrl, command, ShiftDTO.class);
      ShiftDTO resultDTO = testRestTemplate.getForObject(baseUrl + "/" + returnedDTO.getId(), ShiftDTO.class);
      assertEquals(returnedDTO.getId(), resultDTO.getId());

   }

   @Test
   public void updateShiftStartTime_withOnePostedShift_returnsUpdatedShift() {
      CreateShiftCommand command = new CreateShiftCommand();
      command.setName("Délutáni műszak");
      command.setStartTime(LocalTime.parse("19:00:00"));
      command.setDuration(8);
      command.setRestTime(30);

      ShiftDTO returnedDTO = testRestTemplate.postForObject(baseUrl, command, ShiftDTO.class);

      UpdateShiftCommand updateCommand = new UpdateShiftCommand();
      updateCommand.setStartTime(LocalTime.parse("14:00:00"));
      long id = returnedDTO.getId();

      testRestTemplate.put(baseUrl + "/" + id, updateCommand);
      returnedDTO = testRestTemplate.getForObject(baseUrl + "/" + id, ShiftDTO.class);

      assertEquals("Délutáni műszak", returnedDTO.getName());

   }

   @Test
   public void deleteShiftById_withSomePostedShifts_getAllShouldReturnRemainingShifts() {
      CreateShiftCommand command1 = new CreateShiftCommand();
      command1.setName("Műszak1");
      command1.setStartTime(LocalTime.parse("08:00:00"));
      command1.setDuration(8);
      command1.setRestTime(60);
      CreateShiftCommand command2 = new CreateShiftCommand();
      command2.setName("Műszak2");
      command2.setStartTime(LocalTime.parse("10:00:00"));
      command2.setDuration(8);
      command2.setRestTime(60);
      CreateShiftCommand command3 = new CreateShiftCommand();
      command3.setName("Műszak3");
      command3.setStartTime(LocalTime.parse("12:00:00"));
      command3.setDuration(8);
      command3.setRestTime(60);

      testRestTemplate.postForObject(baseUrl, command1, ShiftDTO.class);
      testRestTemplate.postForObject(baseUrl, command2, ShiftDTO.class);
      testRestTemplate.postForObject(baseUrl, command3, ShiftDTO.class);

      List<ShiftDTO> shifts = List.of(testRestTemplate.getForObject(baseUrl + "?prefix=Műszak2", ShiftDTO[].class));
      long id = shifts.get(0).getId();

      testRestTemplate.delete(baseUrl + "/" + id);

      List<ShiftDTO> remainingShifts = List.of(testRestTemplate.getForObject(baseUrl, ShiftDTO[].class));

      assertEquals(2, remainingShifts.size());

   }
}
