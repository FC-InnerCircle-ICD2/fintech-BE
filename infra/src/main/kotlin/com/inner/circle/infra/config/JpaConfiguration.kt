package com.inner.circle.infra.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = [
        "com.inner.circle.infra.repository",
        "com.inner.circle.infra.structure.repository"
    ]
)
@EntityScan(
    basePackages = [
        "com.inner.circle.infra.repository.entity",
        "com.inner.circle.infra.structure.repository.entity"
    ]
)
@Configuration
class JpaConfiguration
