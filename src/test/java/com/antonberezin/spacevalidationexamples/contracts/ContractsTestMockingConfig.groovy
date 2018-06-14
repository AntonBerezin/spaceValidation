package com.antonberezin.spacevalidationexamples.contracts

import com.antonberezin.spacevalidationexamples.services.SpaceService
import com.antonberezin.spacevalidationexamples.services.ValidationHandler
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

class ContractsTestMockingConfig {

    protected DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    SpaceService spaceService() {
        factory.Mock(SpaceService)
    }

    @Bean
    ValidationHandler validationHandler() {
        factory.Mock(ValidationHandler)
    }
}
