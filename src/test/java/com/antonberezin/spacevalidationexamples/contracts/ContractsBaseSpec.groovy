package com.antonberezin.spacevalidationexamples.contracts

import com.antonberezin.spacevalidationexamples.services.SpaceService
import com.antonberezin.spacevalidationexamples.services.ValidationHandler
import com.antonberezin.spacevalidationexamples.web.SpaceController
import com.antonberezin.spacevalidationexamples.web.dto.JobApplication
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@WebMvcTest(SpaceController.class)
@Import([ContractsTestMockingConfig])
class ContractsBaseSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    SpaceService spaceService

    @Autowired
    ValidationHandler validationHandler

    def setup() {
        RestAssuredMockMvc.mockMvc(mvc)
        spaceService.generateId() >> "some-id"
        validationHandler.validate(_ as JobApplication) >> {
            JobApplication applicationForm ->
                if (StringUtils.isNotEmpty(applicationForm.name)) {
                    [:] as Map
                } else {
                    [
                            "name": "Please specify your name",
                            "surname" : "Please specify your surname"
                    ]
                }
        }
    }
}

