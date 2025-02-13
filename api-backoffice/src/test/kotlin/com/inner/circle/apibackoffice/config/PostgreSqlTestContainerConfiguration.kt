package com.inner.circle.apibackoffice.config

import com.github.dockerjava.api.command.CreateContainerCmd
import java.io.BufferedReader
import java.io.InputStreamReader
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class PostgreSqlTestContainerConfiguration :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    companion object {
        private const val IMAGE_TAG = "postgres:16-alpine"
        private const val CONTAINER_NAME = "reusable-postgres"
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val env = applicationContext.environment

        cleanUpExitedContainers()

        val dockerImageName = DockerImageName.parse(IMAGE_TAG)
        val container =
            PostgreSQLContainer<Nothing>(dockerImageName).apply {
                if (env.activeProfiles.contains("local")) {
                    withReuse(true)
                    withUsername("test")
                    withPassword("test")
                }
                withCreateContainerCmdModifier { cmd: CreateContainerCmd ->
                    cmd.withName(CONTAINER_NAME)
                }
                withDatabaseName("payment")
                start()
            }

        val jdbcUrl = container.jdbcUrl
        val username = container.username
        val password = container.password

        System.setProperty(
            "test-container.postgres.port",
            container.firstMappedPort.toString()
        )

        System.setProperty("spring.datasource.url", jdbcUrl)
        System.setProperty("spring.datasource.username", username)
        System.setProperty("spring.datasource.password", password)
    }

    private fun cleanUpExitedContainers() {
        val process =
            ProcessBuilder(
                "docker",
                "ps",
                "-a",
                "--filter",
                "name=${CONTAINER_NAME}",
                "--filter",
                "status=exited",
                "--format",
                "{{.ID}}"
            ).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val containerId = reader.readLine()
        reader.close()

        if (containerId != null) {
            ProcessBuilder("docker", "rm", containerId).start().waitFor()
        }
    }
}
