package com.inner.circle.infra.structure.config

import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class DataSourceConfig {
    @Bean
    @Primary
    fun dataSource(): DataSource {
        val localDataSourceInformation = LocalDataSourceInformation()
        val containerInfo = localDataSourceInformation.getContainerInfo()
        return DataSourceBuilder
            .create()
            .url(containerInfo?.jdbcUrl)
            .username(containerInfo?.username)
            .password(containerInfo?.password)
            .driverClassName("org.postgresql.Driver")
            .build()
    }
}
