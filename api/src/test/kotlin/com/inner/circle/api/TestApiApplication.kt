import com.inner.circle.api.ApiApplication
import com.inner.circle.api.config.PostgreSqlTestContainerConfiguration
import org.springframework.boot.runApplication

object TestApiApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<ApiApplication>(*args) {
            addInitializers(PostgreSqlTestContainerConfiguration())
        }
    }
}
