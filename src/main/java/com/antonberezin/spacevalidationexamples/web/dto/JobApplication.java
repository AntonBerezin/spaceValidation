package com.antonberezin.spacevalidationexamples.web.dto;

import com.antonberezin.spacevalidationexamples.validation.ConditionalNotNull;
import com.antonberezin.spacevalidationexamples.validation.Country;
import com.antonberezin.spacevalidationexamples.validation.OneOf;
import com.antonberezin.spacevalidationexamples.validation.groups.Captain;
import com.antonberezin.spacevalidationexamples.validation.groups.Pilot;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ConditionalNotNull(field = "hasShip"
        , conditionField = "position"
        , conditionValue = "Captain"
        , message = "Please tell us if you have a ship")
public class JobApplication {

    @NotBlank(message = "Please specify your name")
    private String name;

    @NotBlank(message = "Please specify your surname")
    private String surname;

    @NotBlank(message = "Please specify your position")
    @OneOf(values = {"Pilot", "Captain"}, message = "There is no such position")
    private String position;

    @NotBlank(message = "Please specify your country")
    @Country(message = "There is no such country")
    private String country;

    @NotNull(message = "Please specify your experience")
    @Min(groups = Pilot.class, value = 2, message = "You don't have enough practice")
    @Min(groups = Captain.class, value = 4, message = "You don't have enough practice")
    private Integer yearsOfPractice;

    private Boolean hasShip;

    private List<@Valid Recommendation> recommendations;
}
