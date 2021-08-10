package com.codecool.vizsgaremek.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@RestController
@RequestMapping("/api/dateformat")
public class ShowDateFormatController {

   @PostMapping("/date")
   public LocalDate ShowDateFormat() {
      LocalDate date = LocalDate.now();
      System.out.println(date);

      return date;
   }

   @PostMapping("/time")
   public LocalTime ShowTimeFormat() {
      LocalTime time = LocalTime.now();
      System.out.println(time);

      return time;
   }

   @PostMapping("/dateandtime")
   public LocalDateTime ShowDateAndTimepFormat() {
      LocalDateTime dateAndTime = LocalDateTime.now();
      System.out.println(dateAndTime);

      return dateAndTime;
   }

}


