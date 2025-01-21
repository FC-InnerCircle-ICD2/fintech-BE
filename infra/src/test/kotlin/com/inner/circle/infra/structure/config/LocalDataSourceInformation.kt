package com.inner.circle.infra.structure.config

import java.io.BufferedReader
import java.io.InputStreamReader
import org.json.JSONArray

private const val CONTAINER_NAME = "reusable-postgres-api"

class LocalDataSourceInformation {
    fun getContainerInfo(): ContainerInfo? {
        val process =
            ProcessBuilder(
                "docker",
                "inspect",
                CONTAINER_NAME
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
                        return ContainerInfo(jdbcUrl, username, password, hostPort)
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
        val password: String,
        val hostPort: String
    )
}