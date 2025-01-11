package com.inner.circle.infra.structure.config

import javax.sql.DataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource = DataSourceBuilder.create().build()
}
