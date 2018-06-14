package com.antonberezin.spacevalidationexamples.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Map<String, String> errors;
}
