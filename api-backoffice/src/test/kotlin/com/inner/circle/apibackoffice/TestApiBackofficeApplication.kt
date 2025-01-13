import com.inner.circle.apibackoffice.ApiBackofficeApplication
import com.inner.circle.apibackoffice.config.PostgreSqlTestContainerConfiguration
import org.springframework.boot.runApplication

object TestApiBackofficeApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication<ApiBackofficeApplication>(*args) {
            addInitializers(PostgreSqlTestContainerConfiguration())
        }
    }
}
