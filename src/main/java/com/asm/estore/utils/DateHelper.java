package com.asm.estore.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public class DateHelper {
    public static LocalDate convertToLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
