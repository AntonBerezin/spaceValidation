package com.antonberezin.spacevalidationexamples

import com.antonberezin.spacevalidationexamples.services.SpaceService
import com.antonberezin.spacevalidationexamples.services.ValidationHandler
import com.antonberezin.spacevalidationexamples.web.dto.JobApplication
import com.antonberezin.spacevalidationexamples.web.dto.Recommendation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validator

@SpringBootTest(classes = ValidationHandler.class)
@AutoConfigureWebMvc
@Import(ValidationMockingConfig)
class ValidationHandlerSpec extends Specification {

    private static def pilot() {
        return [
                name           : "John",
                surname        : "Smith",
                position       : "Pilot",
                country        : "United Kingdom",
                yearsOfPractice: 2,
                recommendations: [
                        [
                                author: "Jabba the Hutt",
                                text  : "You can always rely on him. He will never lose a cargo!"
                        ] as Recommendation,
                        [
                                author: "Jesse Pinkman",
                                text  : " Great pilot! Yeah science!"
                        ] as Recommendation
                ]
        ]
    }

    private static final def captain() {
        return pilot() << [
                position       : "Captain",
                yearsOfPractice: 4,
                hasShip        : true
        ]
    }

    @Autowired
    SpaceService spaceService

    @Autowired
    private ValidationHandler validationHandler

    def setup() {
        spaceService.countryExists("United Kingdom") >> true
    }

    @Unroll
    def "should accept valid data for #documentName"() throws Exception {
        given:
        JobApplication jobApplication = requestMap
        when:
        def errors = validationHandler.validate(jobApplication)
        then:
        errors.isEmpty()
        where:
        documentName | requestMap
        "pilot"      | pilot()
        "captain"    | captain()
    }

    @Unroll
    def "should fail for #fieldName"() throws Exception {
        given:
        JobApplication jobApplication = requestMap
        when:
        def errors = validationHandler.validate(jobApplication)
        then:
        errors[fieldName] == message
        where:
        requestMap                         | fieldName         | message
        pilot() << [name: null]            | "name"            | "Please specify your name"
        pilot() << [surname: null]         | "surname"         | "Please specify your surname"
        pilot() << [position: null]        | "position"        | "Please specify your position"
        pilot() << [country: null]         | "country"         | "Please specify your country"
        pilot() << [yearsOfPractice: null] | "yearsOfPractice" | "Please specify your experience"
        pilot() << [position: "Passenger"] | "position"        | "There is no such position"
        pilot() << [country: "Narnia"]     | "country"         | "There is no such country"
        captain() << [hasShip: null]       | "hasShip"         | "Please tell us if you have a ship"
        captain() << [yearsOfPractice: 3]  | "yearsOfPractice" | "You don't have enough practice"
        pilot() << [yearsOfPractice: 1]    | "yearsOfPractice" | "You don't have enough practice"

    }
}