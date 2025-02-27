package com.inner.circle.api.config

import com.github.dockerjava.api.command.CreateContainerCmd
import java.io.BufferedReader
import java.io.InputStreamReader
import org.json.JSONArray
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
        if (!isContainerRunning(CONTAINER_NAME)) {
            cleanUpExitedContainers(CONTAINER_NAME)
            val env = applicationContext.environment

            val dockerImageName = DockerImageName.parse(IMAGE_TAG)
            val container =
                PostgreSQLContainer<Nothing>(dockerImageName).apply {
                    if (env.activeProfiles.contains("local")) {
                        withReuse(true)
                    }
                    withCreateContainerCmdModifier { cmd: CreateContainerCmd ->
                        cmd.withName(CONTAINER_NAME)
                    }
                    withDatabaseName("payment")
                    withUsername("test")
                    withPassword("test")
                    withInitScript("sql/schema.sql")
                    start()
                }

            val jdbcUrl = container.jdbcUrl
            val username = container.username
            val password = container.password

            System.setProperty("spring.datasource.url", jdbcUrl)
            System.setProperty("spring.datasource.username", username)
            System.setProperty("spring.datasource.password", password)
        } else {
            val containerInfo = getContainerInfo(CONTAINER_NAME)
            if (containerInfo != null) {
                System.setProperty("spring.datasource.url", containerInfo.jdbcUrl)
                System.setProperty("spring.datasource.username", containerInfo.username)
                System.setProperty("spring.datasource.password", containerInfo.password)
            }
        }
    }

    private fun cleanUpExitedContainers(containerName: String) {
        val process =
            ProcessBuilder(
                "docker",
                "ps",
                "-a",
                "--filter",
                "name=$containerName",
                "--filter",
                "status=exited",
                "--format",
                "{{.ID}}"
            ).start()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            val containerId = reader.readLine()
            if (containerId != null) {
                ProcessBuilder("docker", "rm", containerId).start().waitFor()
            }
        }
    }

    private fun isContainerRunning(containerName: String): Boolean {
        val process =
            ProcessBuilder(
                "docker",
                "ps",
                "--filter",
                "name=$containerName",
                "--filter",
                "status=running",
                "--format",
                "{{.ID}}"
            ).start()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            val containerId = reader.readLine()
            return containerId != null
        }
    }

    private fun getContainerInfo(containerName: String): ContainerInfo? {
        val process =
            ProcessBuilder(
                "docker",
                "inspect",
                containerName
            ).start()

        BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            val output = reader.readText()
            val jsonArray = JSONArray(output)
            if (jsonArray.length() > 0) {
                val jsonObject = jsonArray.getJSONObject(0)
                val networkSettings = jsonObject.getJSONObject("NetworkSettings")
                val ports = networkSettings.getJSONObject("Ports")
                val portBindings = ports.getJSONArray("5432/tcp")
                if (portBindings.length() > 0) {
                    val hostPort = portBindings.getJSONObject(0).getString("HostPort")
                    val envVars = jsonObject.getJSONObject("Config").getJSONArray("Env")
                    var username: String? = null
                    var password: String? = null
                    var dbName: String? = null

                    for (i in 0 until envVars.length()) {
                        val envVar = envVars.getString(i)
                        when {
                            envVar.startsWith("POSTGRES_USER=") ->
                                username =
                                    envVar.substringAfter("=")

                            envVar.startsWith("POSTGRES_PASSWORD=") ->
                                password =
                                    envVar.substringAfter("=")
                            envVar.startsWith("POSTGRES_DB=") -> dbName = envVar.substringAfter("=")
                        }
                    }

                    return if (username != null && password != null && dbName != null) {
                        val jdbcUrl = "jdbc:postgresql://localhost:$hostPort/$dbName"
                        ContainerInfo(jdbcUrl, username, password)
                    } else {
                        null
                    }
                }
            }
        }
        return null
    }

    data class ContainerInfo(
        val jdbcUrl: String,
        val username: String,
        val password: String
    )
}
