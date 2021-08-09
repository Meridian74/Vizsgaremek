package com.codecool.vizsgaremek.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ShiftNotFoundException extends AbstractThrowableProblem {
   public ShiftNotFoundException(long id) {
      super(URI.create("shift/shift-not-found"),
            "Not found!",
            Status.NOT_FOUND,
            String.format("Shift with id %d not found!", id));
   }


}
