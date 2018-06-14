package com.antonberezin.spacevalidationexamples

import com.antonberezin.spacevalidationexamples.services.SpaceService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

@TestConfiguration
class ValidationMockingConfig {
    protected DetachedMockFactory factory = new DetachedMockFactory()

    @Bean
    SpaceService spaceService() {
        factory.Mock(SpaceService)
    }
}
