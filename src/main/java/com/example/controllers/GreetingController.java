package com.example.controllers;

import com.example.controllers.common.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("greeting")
public class GreetingController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Greeting greeting(@Valid RequestCriteria criteria) {
        int hour = criteria.getHour();

        if (hour < 5) {
            return new Greeting("good night");
        } else if (5 <= hour && hour < 16) {
            return new Greeting("good morning");
        } else if (16 <= hour) {
            return new Greeting("good night");
        } else {
            throw new IllegalArgumentException("bean validation doesn't work");
        }

    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBindingException(BindException e) {
        ApiError apiError = new ApiError("request is invalid");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            apiError.addDetail(new ApiError(fieldError.getDefaultMessage(), fieldError.getField()));
        }
        return apiError;
    }

}
