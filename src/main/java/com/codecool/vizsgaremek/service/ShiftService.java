package com.codecool.vizsgaremek.service;

import com.codecool.vizsgaremek.dto.*;
import com.codecool.vizsgaremek.exception.ShiftNotFoundException;
import com.codecool.vizsgaremek.model.Shift;
import com.codecool.vizsgaremek.repository.ShiftRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {

   private ModelMapper modelMapper;
   private ShiftRepository shiftRepository;

   public ShiftService(ModelMapper mm, ShiftRepository sr) {
      this.modelMapper = mm;
      this.shiftRepository = sr;
   }


   public ShiftDTO createShift(CreateShiftCommand command) {
      Shift shift = new Shift();

      shift.setShiftName(command.getName());
      shift.setExpectedStartTime(command.getStartTime());
      shift.setDurationInHours(command.getDuration());
      shift.setRestTimeInMinutes(command.getRestTime());

      shift = shiftRepository.save(shift);
      return modelMapper.map(shift, ShiftDTO.class);
   }

   public ShiftDTO findShiftById(long id) {
      Optional<Shift> optionalShift = shiftRepository.findById(id);

      if (optionalShift.isPresent()) {
         return modelMapper.map(optionalShift.get(), ShiftDTO.class);
      }
      else {
         throw new ShiftNotFoundException(id);
      }
   }

   @Transactional
   public ShiftDTO updateShift(long id, UpdateShiftCommand command) {
      Optional<Shift> optionalShift = shiftRepository.findById(id);

      if (optionalShift.isEmpty()) {
         throw new ShiftNotFoundException(id);
      }

      Shift shift = optionalShift.get();
      if (command.getName() != null) {
         shift.setShiftName(command.getName());
      }
      if (command.getStartTime() != null) {
         shift.setExpectedStartTime(command.getStartTime());
      }
      if (command.getDurations() > 0) {
         shift.setDurationInHours(command.getDurations());
      }

      shift.setRestTimeInMinutes(command.getDurations());

      return modelMapper.map(shift, ShiftDTO.class);
   }

   public void deleteShiftById(long id) {
      Optional<Shift> optionalShift = shiftRepository.findById(id);
      if (optionalShift.isPresent()) {
         shiftRepository.deleteById(id);
      }
      else {
         throw new ShiftNotFoundException(id);
      }
   }

   public List<ShiftDTO> shiftList(Optional<String> prefix) {
      Type targetListType = new TypeToken<List<ShiftDTO>>(){}.getType();

      List<Shift> shiftList = shiftRepository.findAll();
      List<Shift> filtered = shiftList.stream()
            .filter(e -> prefix.isEmpty()
                  || e.getShiftName().toLowerCase().startsWith(prefix.get().toLowerCase()))
            .collect(Collectors.toList());

      return modelMapper.map(filtered, targetListType);
   }

}
