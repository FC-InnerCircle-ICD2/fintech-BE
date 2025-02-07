package com.inner.circle.api.jackson

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.config.ObjectMapperConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@Import(ObjectMapperConfig::class)
@ExtendWith(SpringExtension::class)
class SerializationTest(
    @Autowired private val objectMapper: ObjectMapper
) : StringSpec({
        beforeTest {
            objectMapper.shouldNotBeNull()
        }

        data class SubObject(
            val id: Int,
            @JsonProperty("sub_name")
            val name: String
        )

        data class TestBody(
            val string: String = "John Doe",
            val nullableString: String? = null,
            val int: Int = 3,
            val nullableInt: Int? = null,
            val bigDecimal: BigDecimal = BigDecimal.valueOf(2000L),
            val long: Long = 300L,
            val boolean: Boolean = true,
            val double: Double = 1234.5678,
            val float: Float = 12.34f,
            val javaLocalDate: LocalDate = LocalDate.now(),
            val javaDateTime: LocalDateTime = LocalDateTime.now(),
//            TODO - 테스트 코드에서만 해당 kotlinx.datetime 이 깨진다 추후 확인이 필요 !
//            @JsonSerialize(using = KstDateTimeSerializer::class)
//            val kotlinDateTime: kotlinx.datetime.LocalDateTime =
//                Clock.System.now().toLocalDateTime(
//                    TimeZone.UTC
//                ),
            val uuid: UUID = UUID.randomUUID(),
            val stringList: List<String> = listOf("A", "B", "C"),
            val objectList: List<SubObject> = listOf(SubObject(1, "One"), SubObject(2, "Two")),
            val nullableList: List<String>? = null,
            val stringSet: Set<String> = setOf("X", "Y", "Z"),
            val objectSet: Set<SubObject> = setOf(SubObject(3, "Three"), SubObject(4, "Four")),
            val stringMap: Map<String, String> = mapOf("key1" to "value1", "key2" to "value2"),
            val objectMap: Map<String, SubObject> = mapOf("first" to SubObject(5, "Five"))
        )

        "should serialize and deserialize TestBody correctly" {
            val testBody = TestBody()
            val json = objectMapper.writeValueAsString(testBody)
            val deserializedObjectTestBody = objectMapper.readValue(json, TestBody::class.java)

            deserializedObjectTestBody shouldBe testBody

            val expectedJsonTree = objectMapper.readTree(json)
            val actualJsonTree = objectMapper.readTree(objectMapper.writeValueAsString(testBody))

            expectedJsonTree shouldBe actualJsonTree
        }
    })
