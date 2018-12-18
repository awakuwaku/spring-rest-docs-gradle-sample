package com.example.controllers.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError implements Serializable {
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiError> details = new ArrayList<>();

    public ApiError(String message, String target) {
        this.message = message;
        this.target = target;
    }

    public ApiError(String message) {
        this(message, null);
    }

    public void addDetail(ApiError apiError) {
        details.add(apiError);
    }
}
