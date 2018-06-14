package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method 'POST'
        url '/job-applications/'
        body("""
                        {
                            "name" : "John",
                            "surname" : "Smith",
                            "position" : "Pilot",
                            "country" : "United Kingdom",
                            "yearsOfPractice": 2
                        }
            """
        )
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 201
        body("""
				{
                    "id" : "some-id"
                }
			""")
        headers {
            contentType(applicationJson())
        }
    }
}