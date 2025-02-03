import com.inner.circle.infra.config.DataSourceConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@Import(DataSourceConfig::class)
@DataJpaTest(showSql = true)
@ComponentScan(
    basePackages = ["com.inner.circle.infra.repository"],
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ["com.inner.circle.infra.repository.memorydatabase.*"]
        )
    ]
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AbstractJpaTestWithLocalTestContainer
