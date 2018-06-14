package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method 'POST'
        url '/job-applications/'
        body("""
                        {}
            """
        )
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 400
        body("""
				{
                    "errors" : {
                        "name" : "Please specify your name",
                        "surname" : "Please specify your surname"
                    }
                }
			""")
        headers {
            contentType(applicationJson())
        }
    }
}