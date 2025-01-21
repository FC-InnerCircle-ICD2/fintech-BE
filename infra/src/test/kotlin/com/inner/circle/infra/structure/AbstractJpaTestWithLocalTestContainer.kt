package com.inner.circle.infra.structure

import com.inner.circle.infra.structure.config.DataSourceConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource

@Import(DataSourceConfig::class)
@DataJpaTest(showSql = true)
@ComponentScan(basePackages = ["com.inner.circle.infra.structure.repository"])
@TestPropertySource(locations = ["classpath:application-infra.yaml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class AbstractJpaTestWithLocalTestContainer