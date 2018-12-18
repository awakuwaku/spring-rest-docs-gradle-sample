package com.example.controllers;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class RequestCriteria {
    @Max(24)
    @Min(0)
    private int hour;
}
