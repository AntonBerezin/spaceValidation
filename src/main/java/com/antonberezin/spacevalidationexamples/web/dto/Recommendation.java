package com.antonberezin.spacevalidationexamples.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Recommendation {

    @NotBlank(message = "Please specify author")
    private String author;

    private String text;
}
