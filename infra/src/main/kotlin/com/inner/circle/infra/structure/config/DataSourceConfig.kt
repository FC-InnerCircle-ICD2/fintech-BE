import com.inner.circle.infra.structure.config.LocalDataSourceInformation
import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Profile("local")
@Configuration
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
