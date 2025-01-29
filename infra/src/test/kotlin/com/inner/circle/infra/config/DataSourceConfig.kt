package com.inner.circle.infra.config

import java.util.Properties
import javax.sql.DataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class DataSourceConfig {
    companion object {
        // PostgreSQL Testcontainer 설정
        private val postgreSQLContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
                .apply {
                    withDatabaseName("testdb")
                    withUsername("testuser")
                    withPassword("testpass")
                    start()
                }
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")

        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.inner.circle.infra.repository.entity")
        factory.setJpaProperties(properties)
        return factory
    }

    @Bean
    fun transactionManager(
        entityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.getObject()
        return transactionManager
    }
}
