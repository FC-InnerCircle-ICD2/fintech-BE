package com.inner.circle.infrabackoffice.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = [
        "com.inner.circle.infrabackoffice.repository",
        "com.inner.circle.infrabackoffice.structure.repository"
    ]
)
@EntityScan(
    basePackages = [
        "com.inner.circle.infrabackoffice.repository.entity",
        "com.inner.circle.infrabackoffice.structure.repository.entity"
    ]
)
@Configuration
class JpaConfiguration
