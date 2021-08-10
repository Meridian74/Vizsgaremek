package com.codecool.vizsgaremek.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;

public class DateNotFoundException extends AbstractThrowableProblem {

   public DateNotFoundException(LocalDate date) {
      super(URI.create("attendance/shift-date-not-found"),
            "Date not found!",
            Status.NOT_FOUND,
            String.format("Date of shift * %tF * not found!", date));
   }

}
