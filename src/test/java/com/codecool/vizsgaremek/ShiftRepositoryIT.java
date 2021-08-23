package com.codecool.vizsgaremek;

import com.codecool.vizsgaremek.model.Shift;
import com.codecool.vizsgaremek.repository.ShiftRepository;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ShiftRepositoryIT {

   @Autowired
   ShiftRepository shiftRepository;

   @Test
   void testPersist() {
      Shift shift = new Shift();
      shift.setShiftName("Teszt műszak");
      shift.setExpectedStartTime(LocalTime.parse("10:05:00"));
      shift.setDurationInHours(8);
      shift.setRestTimeInMinutes(30);

      shiftRepository.save(shift);

      List<Shift> shifts = shiftRepository.findAll();
      assertThat(shifts)
            .extracting(Shift::getShiftName)
            .containsExactly("Teszt műszak");

   }

}
